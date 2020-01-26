package com.example.examplemod;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;
import com.sun.net.httpserver.HttpServer;

import net.minecraft.server.MinecraftServer;
import net.minecraft.util.MathHelper;

import net.minecraft.init.Blocks;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;

@Mod(modid = ExampleMod.MODID, version = ExampleMod.VERSION)
public class ExampleMod
{
    private final MinecraftServer server = MinecraftServer.getServer();
    public static final String MODID = "MineMetrics";
    public static final String VERSION = "1.0";
    
    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    // ����������������
    System.out.println("[MineMetric] Metrics is postinitilizating...");
}
    
    @EventHandler
    public void init(FMLPostInitializationEvent event)
    {
	// some example code

        try {
        System.out.println("[MineMetric] Metrics is loading...");
        System.out.println("[MineMetric] Binding 8000 port...");
        HttpServer server = HttpServer.create(new InetSocketAddress(8000), 0);
        System.out.println("[MineMetric] Binding 8000 port... OK!");
        server.createContext("/test", new wwwGetTPS());
        server.setExecutor(null); // creates a default executor
        server.start();
        
        } catch (IOException exception) {
        System.err.println(exception);
        }
        
     }
    
  public static void main(String[] args) throws Exception {
//DUMMY PLACE, DIDNT WORK WITH FORGE
  }
  
      public double getWorldTickTime(final int dimId) {
        return this.server.worldTickTimes.get(dimId) == null ? -1 : MathHelper.average(this.server.worldTickTimes.get(dimId)) * 1.0E-6D;
    }

    public double getMeanTickTime() {
        return MathHelper.average(this.server.tickTimeArray) * 1.0E-6D;
    }

    public double getWorldTPS(final int dimId) {
        return this.getWorldTickTime(dimId) < 0 ? this.getWorldTickTime(dimId) : Math.min(1000.0 / this.getWorldTickTime(dimId), 20);
    }

    public double getMeanTPS() {
        return Math.min(1000.0 / this.getMeanTickTime(), 20);
    }

  static class wwwGetTPS implements HttpHandler {
    public void handle(HttpExchange t) throws IOException {
      byte [] response = "Welcome Real's HowTo test page\n ".getBytes();
      
      t.sendResponseHeaders(200, response.length);
      OutputStream os = t.getResponseBody();
      os.write(response);
      os.close();
    }
  }
}
