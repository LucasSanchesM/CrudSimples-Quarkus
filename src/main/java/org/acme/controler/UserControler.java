package org.acme.controler;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.acme.entity.User;
import org.acme.service.UserService;

import java.util.UUID;

@Path("/users")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class UserControler {

    private final UserService userService;

    public UserControler(UserService userService) {
        this.userService = userService;
    }

    @POST
    public Response criarUsuario(User user){
        userService.createUser(user);
        return Response.ok().build();
    }

    @GET
    public Response findAll(@QueryParam("page") @DefaultValue("0") Integer page,
                            @QueryParam("pageSize") @DefaultValue("10") Integer pageSize){
        return Response.ok(userService.findAllUsers(page, pageSize)).build();
    }

    @GET
    @Path("/{userId}")
    public Response findUserById(@PathParam("userId") UUID userId){
        return Response.ok(userService.findUserById(userId)).build();
    }

    @PUT
    @Path("/{userId}")
    public Response updateUserById(@PathParam("userId") UUID userId,
                                   User user){
        userService.updateUserById(userId, user);
        return Response.ok().build();
    }

    @DELETE
    @Path("/{userId}")
    public Response deleteUserById(@PathParam("userId") UUID userId){
        userService.deleteUserById(userId);
        return Response.noContent().build();
    }
}
