package ca.concordia.eats.controller;

import ca.concordia.eats.dto.Customer;
import ca.concordia.eats.dto.UserCredentials;
import ca.concordia.eats.service.UserService;
import ca.concordia.eats.utils.ConnectionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.Connection;

@Controller
public class UserController {

    @Autowired
    private UserService userService;

    Connection con;

    public UserController() throws IOException {
        this.con = ConnectionUtil.getConnection();
    }

    @GetMapping("/register")
    public String registerUser() {
        return "register";
    }

    @GetMapping("/contact")
    public String contact(HttpSession session, Model model) {
        Customer customer = (Customer) session.getAttribute("user");
        UserCredentials userCredentials = userService.fetchUserCredentialsById(customer.getUserId());

        model.addAttribute("username", userCredentials.getUsername());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("phone", customer.getPhone());
        model.addAttribute("noContactUsLink", true);
        model.addAttribute("noCategoryFilter", true);

        return "contact";
    }

    @GetMapping("/buy")
    public String buy() {
        return "buy";
    }


    /**
     * Uses the db.properties file in resources to retrieve db connection parameters
     * username=<my-username>
     * password=<my-secret-password>
     * url=<jdbc-url>
     *
     * @throws IOException
     */
    @RequestMapping(value = "newuserregister", method = RequestMethod.POST)
    public String newUseRegister(@ModelAttribute("customer") Customer customer) throws IOException {
        userService.createCustomer(customer);
        return "redirect:/";
    }

    @GetMapping("profileDisplay")
    public String profileDisplay(HttpSession session, Model model) {
        if (session.getAttribute("user") == null) return "userLogin";

        Customer customer = (Customer) session.getAttribute("user");
        UserCredentials userCredentials = userService.fetchUserCredentialsById(customer.getUserId());

        model.addAttribute("username", userCredentials.getUsername());
        model.addAttribute("password", userCredentials.getPassword());
        model.addAttribute("email", customer.getEmail());
        model.addAttribute("address", customer.getAddress());
        model.addAttribute("phone", customer.getPhone());
        model.addAttribute("noCategoryFilter", true);

        return "updateProfile";
    }

    @RequestMapping(value = "updateuser", method = RequestMethod.POST)
    public String updateUserProfile(HttpSession session,
                                    @RequestParam("username") String username,
                                    @RequestParam("password") String password,
                                    @RequestParam("email") String email,
                                    @RequestParam("phone") String phone,
                                    @RequestParam("address") String address) {

        if (session.getAttribute("user") == null) return "userLogin";

        Customer customer = (Customer) session.getAttribute("user");
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);

        UserCredentials userCredentials = new UserCredentials(username, password);

        userService.updateUserProfile(customer, userCredentials);

        return "redirect:/index";
    }

}
