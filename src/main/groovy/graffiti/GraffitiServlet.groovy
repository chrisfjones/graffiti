package graffiti

import javax.servlet.http.*
import org.eclipse.jetty.websocket.WebSocketServlet
import org.eclipse.jetty.websocket.WebSocket

class GraffitiServlet extends WebSocketServlet {

    def get, post, put, delete, webSocket

    public void doGet(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response, get)
    }

    public void doPost(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response, post)
    }

    protected void doPut(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response, put)
    }

    protected void doDelete(HttpServletRequest request, HttpServletResponse response) {
        execute(request, response, delete)
    }

    WebSocket doWebSocketConnect(HttpServletRequest request, String s) {
        WebContextHolder.instance.setup( request, null)
        WebSocket output = webSocket()
        WebContextHolder.instance.cleanup()
        return output
    }

    private void execute(request, response, block) {
        if( block ) {
            WebContextHolder.instance.setup( request, response )
            def output = block()
            if( output instanceof String || output instanceof GString ) {
                response.writer.write( output.toString() )
            }
            WebContextHolder.instance.cleanup()
        } else {
            response.sendError(HttpServletResponse.SC_METHOD_NOT_ALLOWED)
        }
    }

}

