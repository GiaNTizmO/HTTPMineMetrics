package com.example.examplemod;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    public static final String MODID = "MineMetrics";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    // ����������������
    System.out.println("[MineMetric] Metrics is preinitilizating...");
}
    
    @EventHandler
    public void init(FMLInitializationEvent event)
    {
	// some example code

        try {
        System.out.println("[MineMetric] Metrics is loading...");
        System.out.println("[MineMetric] Binding 8000 port...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("[MineMetric] Binding 8000 port... OK!");
        server.createContext("/test", new MyHandler());
        server.setExecutor(null); // creates a default executor
        server.start();
        
        } catch (IOException exception) {
        System.err.println(exception);
        }
        
    }
    
  public static void main(String[] args) throws Exception {

  }

  static class MyHandler implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
      byte [] response = "Welcome Real's HowTo test page".getBytes();
      t.sendResponseHeaders(200, response.length);
      OutputStream os = t.getResponseBody();
      os.write(response);
      os.close();
    }
  }
}
