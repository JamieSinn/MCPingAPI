package ca.jamiesinn.MCPingAPI;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;

public class MCPingAPI
{

    public MinecraftPing getPing(String hostname, int port) throws IOException
    {
        Socket socket = null;
        DataInputStream in = null;
        DataOutputStream out = null;
        socket = new Socket();
        socket.connect(new InetSocketAddress(hostname, port), 3000);
        in = new DataInputStream(socket.getInputStream());
        out = new DataOutputStream(socket.getOutputStream());
        out.write(254);
        if (in.read() != 255)
            throw new IOException("Bad message");
        short bit = in.readShort();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < bit; ++i)
            sb.append(in.readChar());
        String[] bits = sb.toString().split("\u00a7");
        if (bits.length != 3)
            throw new IOException("Bad message");
        MinecraftPing ret = new MinecraftPing();
        ret.motd = bits[0];
        ret.players = Integer.valueOf(bits[1]);
        ret.maxPlayers = Integer.valueOf(bits[2]);
        return ret;
    }

    public class MinecraftPing
    {
        public String motd;
        public int players;
        public int maxPlayers;
    }

}
