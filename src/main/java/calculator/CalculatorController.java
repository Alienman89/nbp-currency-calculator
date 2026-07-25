package calculator;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
public class CalculatorController {

    private final NbpApiService apiService;

    public CalculatorController(NbpApiService apiService) {
        this.apiService = apiService;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("currencies", apiService.fetchRates());
        model.addAttribute("amount", 1000.0);
        model.addAttribute("fee", 1.5);
        return "calculator"; // <-- ZMIANA TUTAJ
    }

    @PostMapping("/calculate")
    public String calculate(
            @RequestParam double amount,
            @RequestParam String fromCode,
            @RequestParam String toCode,
            @RequestParam double fee,
            Model model
    ) {
        List<NbpApiService.CurrencyRate> rates = apiService.fetchRates();

        NbpApiService.CurrencyRate from = rates.stream().filter(r -> r.code().equals(fromCode)).findFirst().orElse(rates.get(0));
        NbpApiService.CurrencyRate to = rates.stream().filter(r -> r.code().equals(toCode)).findFirst().orElse(rates.get(0));

        double amountInPln = amount * from.ask();
        double finalAmount = (amountInPln / to.ask()) * (1 - fee / 100.0);

        double idealFinalAmount = (amount * from.mid()) / to.mid();
        double totalCostPln = (idealFinalAmount - finalAmount) * to.mid();

        model.addAttribute("currencies", rates);
        model.addAttribute("amount", amount);
        model.addAttribute("fee", fee);
        model.addAttribute("fromCode", fromCode);
        model.addAttribute("toCode", toCode);

        model.addAttribute("result", String.format("%.2f", finalAmount));
        model.addAttribute("totalCost", String.format("%.2f", Math.max(0, totalCostPln)));

        return "calculator";
    }
}