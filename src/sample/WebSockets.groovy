#!/usr/bin/env groovy
@Grab('com.goodercode:graffiti:1.0-ws-SNAPSHOT')

import graffiti.Graffiti
import org.eclipse.jetty.websocket.WebSocket
import org.eclipse.jetty.websocket.WebSocket.Connection
import graffiti.Get

@Get('/ws')
def get() {
    return "connect via websocket for magic"
}

@graffiti.WebSocket('/ws')
def chat() {
    println 'websockt!'
    return new WebSocket.OnTextMessage() {
        Connection c
        void onOpen(Connection connection) {
            c = connection
            println 'websock opn!'
        }

        void onClose(int i, String s) {
            println 'websock close!'
        }

        void onMessage(String s) {
            println "message-->$s"
            // todo: multicast
            c.sendMessage(s)
        }
    }
}

Graffiti.root ""
Graffiti.config["port"] = '8081'
Graffiti.serve '*.html'
Graffiti.serve this
Graffiti.start()

//Graffiti.server.stop()
