package org.example.servlet;

import com.github.mustachejava.DefaultMustacheFactory;
import com.github.mustachejava.Mustache;
import com.github.mustachejava.MustacheFactory;
import lombok.NoArgsConstructor;
import lombok.SneakyThrows;
import org.example.dao.CarDAO;
import org.example.entity.Car;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@WebServlet(urlPatterns = {"/car"})
@NoArgsConstructor
public class CarServlet extends HttpServlet {
    private CarDAO carDAO;

    @Override
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        carDAO = (CarDAO) getServletContext().getAttribute("carDAO");
    }

    @SneakyThrows
    protected void doGet(HttpServletRequest request, HttpServletResponse response) {
        MustacheFactory mf = new DefaultMustacheFactory();
        Mustache mustache = mf.compile("templates/index.mustache");

        List<Car> car = carDAO.getAllCars();

        Map<String, Object> model = new HashMap<>();
        model.put("car", car);

        mustache.execute(response.getWriter(), model);
    }

    @SneakyThrows
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        String name = request.getParameter("car_model");
        BigDecimal price = new BigDecimal(request.getParameter("price"));
        String photoUrl = request.getParameter("photoUrl");
        String model = request.getParameter("car_model");

        Car car = Car.builder()
                .name(name)
                .model(model)
                .price(price)
                .photoUrl(photoUrl)
                .build();

        carDAO.addCars(car);
        response.sendRedirect(request.getContextPath() + "/car");
    }
}
