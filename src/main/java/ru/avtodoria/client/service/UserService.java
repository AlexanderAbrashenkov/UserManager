package ru.avtodoria.client.service;

import org.fusesource.restygwt.client.Attribute;
import org.fusesource.restygwt.client.MethodCallback;
import org.fusesource.restygwt.client.RestService;
import ru.avtodoria.shared.dto.UserDto;

import javax.ws.rs.*;
import java.util.List;

/**
 * RestyGWT CRUD methods for communication with server
 */
public interface UserService extends RestService {
    @GET
    @Path("users")
    public void getUserList(MethodCallback<List<UserDto>> callback);

    @POST
    @Path("users")
    public void saveUser(UserDto userDto, MethodCallback<Integer> id);

    @PUT
    @Path("users/{id}")
    public void updateUser(@PathParam("id") @Attribute("id") UserDto userDto, MethodCallback<UserDto> callback);

    @DELETE
    @Path("users/{id}")
    public void removeUser(@PathParam("id") @Attribute("id") UserDto userDto, MethodCallback<Boolean> callback);
}
