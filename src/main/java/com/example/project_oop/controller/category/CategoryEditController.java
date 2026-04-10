package com.example.project_oop.controller.category;

import com.example.project_oop.models.Category;
import com.example.project_oop.service.CategoryService;

import javafx.scene.control.*;

import java.util.Optional;

public class CategoryEditController{

    private final CategoryService categoryService;

    public CategoryEditController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    public boolean show(Category cat){
        Dialog<String> dialog = CategoryAddController.buildDialog(
            "Chỉnh Sửa Thể Loại",
            "Cập nhật tên thể loại (ID: " + cat.getId() + "):",
            cat.getName()
        );

        Optional<String> result = dialog.showAndWait();
        if(result.isEmpty()) return false;

        String newName = result.get();
        if(newName.equals(cat.getName())) return false;

        if(categoryService.existsByName(newName)){
            showAlert(Alert.AlertType.WARNING, "Trùng tên", "Thể loại \"" + newName + "\" đã tồn tại!");
            return false;
        }
        try{
            categoryService.updateCategory(cat.getId(), newName);
            showAlert(Alert.AlertType.INFORMATION, "Thành công", "Đã đổi tên thành \"" + newName + "\"!");
            return true;
        }
        catch(Exception e){
            showAlert(Alert.AlertType.ERROR, "Thất bại", "Không thể cập nhật thể loại. Vui lòng kiểm tra kết nối CSDL.");
            return false;
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
