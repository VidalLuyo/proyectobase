package vallegrande.edu.pe.controller;

 import org.springframework.web.bind.annotation.*;
 import vallegrande.edu.pe.model.Product;
 import vallegrande.edu.pe.model.LoginRequest;
 import vallegrande.edu.pe.model.LoginResponse;
 import vallegrande.edu.pe.service.ProductService;

 import java.util.List;

@RestController
public class ApiController {

    private final ProductService productService;

    public ApiController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String home() {
        String message = "API funcionando";
        String unused = "No se usa"; // ❌ variable innecesaria
        return message;
    }

    @GetMapping("/products")
    public List<Product> getProducts() {
        return productService.getAll();
    }

    @PostMapping("/login")
    public LoginResponse login(@RequestBody LoginRequest request) {
        // Simulación simple de login para pruebas de JMeter
        if ("admin".equals(request.getUsername()) && "password".equals(request.getPassword())) {
            return new LoginResponse(true, "Login exitoso", "token-123456");
        }
        return new LoginResponse(false, "Credenciales inválidas", null);
    }

    // ❌ Método innecesariamente complejo
    @GetMapping("/check")
    public String check() {
        int a = 1;
        int b = 2;
        int c = 3;

        if (a < b) {
            if (b < c) {
                if (c > a) {
                    return "OK";
                }
            }
        }
        return "FAIL";
    }
    
    @GetMapping("/ping")
    public String ping() {
        return "OK";
    }
}