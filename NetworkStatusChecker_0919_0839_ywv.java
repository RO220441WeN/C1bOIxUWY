// 代码生成时间: 2025-09-19 08:39:41
import akka.actor.AbstractActor;
import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.actor.Terminated;
import akka.cluster.Cluster;
import akka.cluster.MemberStatus;
import akka.cluster.ClusterEvent;
import akka.cluster.ClusterEvent.MemberUp;
import akka.cluster.ClusterEvent.MemberRemoved;
import akka.cluster.ClusterEvent.MemberExited;
import akka.cluster.ClusterEvent.MemberDowned;
import akka.cluster.ClusterEvent.UnreachableMember;
import akka.cluster.ClusterEvent.ReachableMember;
import akka.cluster.ClusterEvent.CurrentClusterState;
import akka.cluster.ClusterEvent.ClusterMetricsChanged;
import akka.cluster.ClusterEvent.ClusterShuttingDown;
import akka.cluster.ClusterEvent.ClusterMerging;
import akka.cluster.ClusterEvent.ClusterNodeInfo;
import akka.cluster.ClusterEvent.ClusterEventInitialJoin;
import akka.cluster.Member;
import akka.cluster.UniqueAddress;
import akka.cluster.ClusterSettingsData;
import akka.cluster.ClusterSettingsAdapter;
import akka.cluster.ClusterSettingsData.SeedNodes;
import akka.io.Tcp;
import akka.io.TcpMessage;
import akka.io.TcpConnection;
import scala.concurrent.Future;
import scala.concurrent.duration.Duration;
import scala.concurrent.duration.FiniteDuration;
import java.net.InetAddress;
import akka.util.ByteString;
import akka.util.ByteString$;
import java.io.IOException;
import akka.Done;
import java.net.Socket;
import java.net.SocketTimeoutException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

public class NetworkStatusChecker extends AbstractActor {
    
    private final ActorRef tcpManager;
    private final InetAddress host;
    private final int port;
    private final FiniteDuration timeout;
    
    public NetworkStatusChecker(InetAddress host, int port, FiniteDuration timeout) {
        this.host = host;
        this.port = port;
        this.timeout = timeout;
        this.tcpManager = Tcp.get(getContext().getSystem()).manager();
    }
    
    @Override
    public Receive createReceive() {
        return receiveBuilder()
            .match(TcpMessage.Connected.class, this::handleConnected)
            .match(Tcp.Connection.class, this::handleConnection)
            .match(ByteString.class, this::handleMessage)
            .match(TcpMessage.Disconnected.class, this::handleDisconnected)
            .match(Tcp.CommandFailed.class, this::handleCommandFailed)
            .build();
    }
    
    private void handleConnected(TcpMessage.Connected c) {
        getContext().watch(c.getHandler());
        tcpManager.tell(new TcpMessage.Write(ByteString$.MODULE$.fromString("Checking connection..."), c.getHandler()), getSelf());
    }
    
    private void handleConnection(Tcp.Connection conn) {
        getContext().watch(conn.getActor());
        getContext().watch(tcpManager);
        tcpManager.tell(new TcpMessage.Connected(conn.getActor(), conn.getChannel()), getSelf());
    }
    
    private void handleMessage(ByteString msg) {
        getSender().tell(Done.getInstance(), getSelf());
    }
    
    private void handleDisconnected(TcpMessage.Disconnected disco) {
        getSender().tell(Done.getInstance(), getSelf());
    }
    
    private void handleCommandFailed(Tcp.CommandFailed cmdFailed) {
        Throwable cause = cmdFailed.getCmd() instanceof TcpMessage.Connect ?
                "Connection failed " + host : "Write failed";
        getSender().tell(new Status.Failure(cause), getSelf());
    }
    
    public static void main(String[] args) throws Exception {
        ActorSystem system = ActorSystem.create("NetworkStatusChecker");
        FiniteDuration timeout = FiniteDuration.apply(10, TimeUnit.SECONDS);
        InetAddress host = InetAddress.getByName("example.com");
        int port = 80;
        ActorRef actor = system.actorOf(Props.create(NetworkStatusChecker.class, host, port, timeout));
        
        try {
            actor.tell(new TcpMessage.Connect(host, port), ActorRef.noSender());
            system.log().info("Checking network status...");
        } catch (Exception e) {
            system.log().error("Error checking network status", e);
        }
    }
    
    // Inner class to handle the network status check
    private static class Status extends AbstractActor {
        public static class Success implements java.io.Serializable {
            public final String message;
            public Success(String message) {
                this.message = message;
            }
        }
        public static class Failure implements java.io.Serializable {
            public final Throwable cause;
            public Failure(Throwable cause) {
                this.cause = cause;
            }
        }
        
        @Override
        public Receive createReceive() {
            return receiveBuilder()
                    .match(Status.Success.class, this::handleSuccess)
                    .match(Status.Failure.class, this::handleFailure)
                    .build();
        }
        
        private void handleSuccess(Status.Success success) {
            getSender().tell(success.message, getSelf());
        }
        
        private void handleFailure(Status.Failure failure) {
            getSender().tell(failure.cause.getMessage(), getSelf());
        }
    }
}
