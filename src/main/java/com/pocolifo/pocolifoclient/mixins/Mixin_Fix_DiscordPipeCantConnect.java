/*
 * TODO: THIS FIX DOES NOT WORK, FIX AT SOME POINT
 * Exception in thread "Thread-8" java.lang.VerifyError: Bad type on operand stack
Exception Details:
  Location:
    com/jagrosh/discordipc/entities/pipe/Pipe.createPipe(Lcom/jagrosh/discordipc/IPCClient;Ljava/util/HashMap;Ljava/lang/String;)Lcom/jagrosh/discordipc/entities/pipe/Pipe; @74: invokestatic
  Reason:
    Type uninitialized 60 (current frame, stack[1]) is not assignable to 'java/lang/RuntimeException'
  Current Frame:
    bci: @74
    flags: { }
    locals: { 'com/jagrosh/discordipc/IPCClient', 'java/util/HashMap', 'java/lang/String', 'java/lang/String', 'java/io/IOException', uninitialized 60, 'java/io/IOException' }
    stack: { uninitialized 60, uninitialized 60, 'java/io/IOException' }
  Bytecode:
    0x0000000: 12ca b800 cfb6 00d2 4e2d 12d4 b600 d899
    0x0000010: 000e bb00 da59 2a2b 2cb7 00dd b02d 12df
    0x0000020: b600 d89a 000c 2d12 e1b6 00d8 9900 22bb
    0x0000030: 00e3 592a 2b2c b700 e4b0 3a04 bb00 e659
    0x0000040: 1904 3a06 3a05 1905 1906 b800 eabf bb00
    0x0000050: e659 bb00 ec59 b700 ed12 efb6 00f3 2db6
    0x0000060: 00f3 b600 f4b7 00f6 bf
  Exception Handler Table:
    bci [47, 57] => handler: 58
  Stackmap Table:
    append_frame(@29,Object[#74])
    same_frame(@47)
    same_locals_1_stack_item_frame(@58,Object[#53])
    same_frame(@78)

	at com.jagrosh.discordipc.IPCClient.connect(IPCClient.java:116)
	at com.pocolifo.pocolifoclient.mods.mods.discord.DiscordUpdateHandler.start(DiscordUpdateHandler.java:46)
	at com.pocolifo.pocolifoclient.mods.mods.discord.DiscordMod.enable(DiscordMod.java:38)
	at com.pocolifo.pocolifoclient.mods.ModLoader.registerMod(ModLoader.java:63)
	at com.pocolifo.pocolifoclient.PocolifoClient.initModLoader(PocolifoClient.java:68)
	at com.pocolifo.pocolifoclient.PocolifoClient.<init>(PocolifoClient.java:49)
	at com.pocolifo.pocolifoclient.PocolifoClient.init(PocolifoClient.java:45)
	at com.pocolifo.pocolifoclient.splash.loading.InitializationThread.run(InitializationThread.java:31)
 *
 *
 * This can occur on Linux (tested on arch):
 *
 * java.lang.RuntimeException: org.newsclub.net.unix.AFUNIXSocketException: No such file or directory (socket: /run/user/1000/discord-ipc-1)
	at com.jagrosh.discordipc.entities.pipe.Pipe.createPipe(Pipe.java:167)
	at com.jagrosh.discordipc.entities.pipe.Pipe.openPipe(Pipe.java:67)
	at com.jagrosh.discordipc.IPCClient.connect(IPCClient.java:116)
	at com.pocolifo.pocolifoclient.mods.mods.discord.DiscordUpdateHandler.start(DiscordUpdateHandler.java:46)
	at com.pocolifo.pocolifoclient.mods.mods.discord.DiscordMod.enable(DiscordMod.java:38)
	at com.pocolifo.pocolifoclient.mods.ModLoader.registerMod(ModLoader.java:63)
	at com.pocolifo.pocolifoclient.PocolifoClient.initModLoader(PocolifoClient.java:68)
	at com.pocolifo.pocolifoclient.PocolifoClient.<init>(PocolifoClient.java:49)
	at com.pocolifo.pocolifoclient.PocolifoClient.init(PocolifoClient.java:45)
	at com.pocolifo.pocolifoclient.splash.loading.InitializationThread.run(InitializationThread.java:31)
Caused by: org.newsclub.net.unix.AFUNIXSocketException: No such file or directory (socket: /run/user/1000/discord-ipc-1)
	at org.newsclub.net.unix.NativeUnixSocket.connect(Native Method)
	at org.newsclub.net.unix.AFUNIXSocketImpl.connect(AFUNIXSocketImpl.java:136)
	at org.newsclub.net.unix.AFUNIXSocket.connect(AFUNIXSocket.java:107)
	at org.newsclub.net.unix.AFUNIXSocket.connect(AFUNIXSocket.java:98)
	at com.jagrosh.discordipc.entities.pipe.UnixPipe.<init>(UnixPipe.java:46)
	at com.jagrosh.discordipc.entities.pipe.Pipe.createPipe(Pipe.java:163)
	... 9 more
 *
 * DiscordIPC is essentially throwing an exception that it does not catch which
 * prevents the Discord presence from actually starting.
 *
 * The fix simply throws an exception that DiscordIPC does catch.
 */

/*package com.pocolifo.pocolifoclient.mixins;

import com.jagrosh.discordipc.entities.pipe.Pipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.io.IOException;

@Mixin(Pipe.class)
public class Mixin_Fix_DiscordPipeCantConnect {
    @Redirect(method = "createPipe", at = @At(value = "INVOKE", target = "Ljava/lang/RuntimeException;<init>(Ljava/lang/Throwable;)V"))
    private static void useDifferentException(RuntimeException instance, Throwable throwable) throws IOException {
        throw new IOException(throwable);
    }

}
*/