package com.example.project_oop.controller.category;

import com.example.project_oop.models.Category;
import com.example.project_oop.service.CategoryService;

import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;

import java.util.Optional;

public class CategoryDeleteController{

    private final CategoryService categoryService;

    public CategoryDeleteController(CategoryService categoryService){
        this.categoryService = categoryService;
    }

    public boolean show(Category cat){
        Alert confirm = new Alert(AlertType.CONFIRMATION);
        confirm.setTitle("Xác nhận xóa");
        confirm.setHeaderText(null);
        confirm.setContentText("Bạn có chắc muốn xóa thể loại \"" + cat.getName() + "\" không?\n" + "Thao tác này không thể hoàn tác.");

        Optional<ButtonType> choice = confirm.showAndWait();
        if (choice.isEmpty() || choice.get() != ButtonType.OK) return false;

        try{
            categoryService.deleteCategory(cat.getId());
            showAlert(AlertType.INFORMATION, "Đã xóa", "Thể loại \"" + cat.getName() + "\" đã được xóa.");
            return true;
        }
        catch(IllegalStateException e){
            showAlert(AlertType.WARNING, "Không thể xóa", "Thể loại \"" + cat.getName() + "\" đang được dùng bởi một hoặc nhiều sách.\n" + "Vui lòng chuyển các sách sang thể loại khác trước khi xóa.");
            return false;
        }
        catch(Exception e){
            showAlert(AlertType.ERROR, "Thất bại", "Không thể xóa thể loại. Vui lòng kiểm tra kết nối CSDL.");
            return false;
        }
    }

    private void showAlert(AlertType type, String title, String message){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
