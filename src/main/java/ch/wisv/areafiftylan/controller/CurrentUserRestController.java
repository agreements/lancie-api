package ch.wisv.areafiftylan.controller;


import ch.wisv.areafiftylan.dto.UserDTO;
import ch.wisv.areafiftylan.model.Order;
import ch.wisv.areafiftylan.model.User;
import ch.wisv.areafiftylan.service.OrderService;
import ch.wisv.areafiftylan.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static ch.wisv.areafiftylan.util.ResponseEntityBuilder.createResponseEntity;

@RestController
@RequestMapping("/users/current")
public class CurrentUserRestController {

    private UserService userService;

    private OrderService orderService;

    @Autowired
    CurrentUserRestController(UserService userService, OrderService orderService) {
        this.userService = userService;
        this.orderService = orderService;
    }

    /**
     * Get the User currently logged in. Because our User model implements the Spring Security UserDetails, this can be
     * directly derived from the Authentication object which is automatically added. Returns a not-found entity if
     * there's no user logged in. Returns the user
     *
     * @param auth Current Authentication object, automatically taken from the SecurityContext
     *
     * @return The currently logged in User.
     */
    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<?> getCurrentUser(Authentication auth) {
        if(auth != null) {
            // Get the currently logged in user from the autowired Authentication object.
            UserDetails currentUser = (UserDetails) auth.getPrincipal();
            User user = userService.getUserByUsername(currentUser.getUsername()).get();
            return new ResponseEntity<>(user, HttpStatus.OK);
        } else {
            return createResponseEntity(HttpStatus.OK, "Not logged in");
        }
    }

    /**
     * This method accepts PUT requests on /users/current. It replaces all fields with the new user provided in the
     * RequestBody and resets the profile fields. All references to the old user are maintained (Team membership ect).
     *
     * This is limited to admin use, because users should not be able to change their core information with a
     * single PUT request. Use the profile mapping for editing profile fields.
     *
     * @param input A UserDTO object containing data of the new user
     *
     * @return The User object.
     */
    @RequestMapping(method = RequestMethod.PUT)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> replaceCurrentUser(@Validated @RequestBody UserDTO input, Authentication auth) {
        User user = (User) auth.getPrincipal();
        user = userService.replace(user.getId(), input);
        return createResponseEntity(HttpStatus.OK, "User successfully replaced", user);
    }

    /**
     * Get all Orders that the current User created. This doesn't include expired orders. This will be a Collection with
     * size 0 or 1 of the majority, but it can contain more orders.
     *
     * @param auth The current User
     *
     * @return A collection of Orders of the current User.
     */
    @RequestMapping(value = "/orders", method = RequestMethod.GET)
    public Collection<Order> getAllOrders(Authentication auth) {
        UserDetails currentUser = (UserDetails) auth.getPrincipal();
        return orderService.findOrdersByUsername(currentUser.getUsername());
    }

    /**
     * Get the current open order include expired orders. A User van only have one open order.
     *
     * @param auth The current User, injected by Spring
     *
     * @return The current open order, if any exist
     */
    @RequestMapping(value = "/orders/open", method = RequestMethod.GET)
    public Order getOpenOrder(Authentication auth) {
        UserDetails currentUser = (UserDetails) auth.getPrincipal();
        return orderService.getOpenOrder(currentUser.getUsername());
    }
}
