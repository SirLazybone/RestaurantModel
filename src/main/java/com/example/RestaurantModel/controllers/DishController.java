package com.example.RestaurantModel.controllers;

import com.example.RestaurantModel.data.models.Dish;
import com.example.RestaurantModel.data.models.UserEntity;
import com.example.RestaurantModel.services.DishService;
import com.example.RestaurantModel.services.DishService;
import com.example.RestaurantModel.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Controller
@RequestMapping()
public class DishController {
    @Autowired
    private DishService dishService;
    @Autowired
    private UserService userService;


    @GetMapping("admin/new")
    public String newDishAdmin(Model model) {
        model.addAttribute("dish", new Dish());
        return "admin/new";
    }

    @PostMapping("admin/add")
    public String addDishAdmin(@ModelAttribute("dish") Dish dish) {
        Dish newDish = dishService.addDish(dish);
        return "redirect:/admin/menu";
    }

    @DeleteMapping("admin/delete/{id}")
    public String deleteDishAdmin(@PathVariable("id") UUID id) {
        boolean deleted = dishService.deleteDish(id);
        return "redirect:/admin/menu";
    }

//    //TODO: complete update dish (change id for name, but i suppose that update will be done via form in show)
//    @PutMapping("/update/{id}")
//    public ResponseEntity<Dish> updateDishAdmin(@PathVariable UUID id, @RequestBody Dish dish) {
//        Dish updatedDish = dishService.updateDish(id, dish);
//        return ResponseEntity.ok(updatedDish);
//    }

    @GetMapping("/admin/menu")
    public String getMenuAdmin(Model model) throws Exception {
        List<Dish> dishes = dishService.getAllDishes();
        model.addAttribute("dishes", dishes);
        UserEntity admin = userService.getUserByName("admin");
        model.addAttribute("admin", admin);
        return "admin/menu";
    }

    @GetMapping("admin/{id}")
    public String showAdmin(Model model, @PathVariable UUID id) { //TODO: change id for name
        Dish dish = dishService.getDishById(id);
        model.addAttribute("dish", dish);
        return "admin/show";
    }

    @PatchMapping("admin/update/{id}")
    public String updateDish(@PathVariable("id") UUID id, @ModelAttribute("dish") Dish updatedDish) {
        Dish dish = dishService.updateDish(id, updatedDish);
        return "redirect:/admin/menu";
    }


}
