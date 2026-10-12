package org.acme.exception;

import io.netty.handler.codec.http.HttpStatusClass;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.ExceptionMapper;

public class UserDontFoundExceptionMapper implements ExceptionMapper<UserDontFoundException> {
    @Override
    public Response toResponse(UserDontFoundException exception) {
        return Response.status(Response.Status.NOT_FOUND.getStatusCode(), "User dont found").build();
    }
}
