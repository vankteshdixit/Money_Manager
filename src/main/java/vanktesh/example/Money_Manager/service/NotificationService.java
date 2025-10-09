package vanktesh.example.Money_Manager.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import vanktesh.example.Money_Manager.dto.ExpenseDTO;
import vanktesh.example.Money_Manager.entity.ProfileEntity;
import vanktesh.example.Money_Manager.repository.ProfileRepository;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {
    private final ProfileRepository profileRepository;
    private final EmailService emailService;
    private final ExpenseService expenseService;

    @Value("${money.manager.frontend.url}")
    private String frontendUrl;

    @Scheduled(cron ="0 0 22 * * *", zone = "IST")
    public void sendDailyIncomeExpenseRemainder(){
        log.info("Job started: sendDailyIncomeExpenseRemainder()");
        List<ProfileEntity> profiles = profileRepository.findAll();
        for (ProfileEntity profile : profiles) {
            String body = "<!DOCTYPE html>"
                    + "<html>"
                    + "<head>"
                    + "<meta charset='UTF-8'>"
                    + "<meta name='viewport' content='width=device-width, initial-scale=1.0'>"
                    + "<style>"
                    + "body {font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;}"
                    + ".container {max-width: 600px; background: #ffffff; margin: 40px auto; border-radius: 8px; "
                    + "box-shadow: 0 2px 8px rgba(0,0,0,0.1); padding: 30px;}"
                    + "h2 {color: #333333;}"
                    + "p {color: #555555; font-size: 16px; line-height: 1.6;}"
                    + ".button {display: inline-block; padding: 12px 24px; background-color: #4CAF50; "
                    + "color: white; text-decoration: none; border-radius: 6px; font-weight: bold; margin-top: 15px;}"
                    + ".footer {margin-top: 30px; font-size: 14px; color: #777777;}"
                    + "</style>"
                    + "</head>"
                    + "<body>"
                    + "<div class='container'>"
                    + "<h2>Hi " + profile.getFullName() + ",</h2>"
                    + "<p>This is a friendly reminder to add your income and expenses for today in <b>Money Manager</b>.</p>"
                    + "<a href='" + frontendUrl + "' class='button'>Go to Money Manager</a>"
                    + "<p class='footer'>Best regards,<br><b>Money Manager Team</b></p>"
                    + "</div>"
                    + "</body>"
                    + "</html>";

            emailService.sendEmail(profile.getEmail(),
                    "Daily Reminder: Add Your Income and Expenses", body);

        }
        log.info("Job completed: sendDailyIncomeExpenseRemainder()");

    }
//    @Scheduled(cron ="0 0 23 * * *", zone = "IST")
@Scheduled(cron ="0 0 23 * * *", zone = "IST")
    public void sendDailyExpenseSummary(){
        log.info("Job started: sendDailyExpenseSummary()");
        List<ProfileEntity> profiles = profileRepository.findAll();
        for (ProfileEntity profile : profiles){
            List<ExpenseDTO> todayExpenses = expenseService.getExpensesForUserOnDate(profile.getId(), LocalDate.now(ZoneId.of("Asia/Kolkata")));
            if(!todayExpenses.isEmpty()) {
                StringBuilder table = new StringBuilder();
                table.append("<table style = 'border-collapse:collapse;width:100%;'>");
                table.append("<tr style = 'background-color:#f2f2f2;'><th style = 'border:1px solid #ddd;padding:8px;'>Category</th><th style = 'border:1px solid #ddd;padding:8px;'>Date</th><th>");
                int i= 1;
                for (ExpenseDTO expense : todayExpenses){
                    table.append("<tr>");
                    table.append("<td style = 'border:1px solid #ddd;padding:8px;'>").append(i++).append("</td>");
                    table.append("<td style = 'border:1px solid #ddd;padding:8px;'>").append(expense.getName()).append("</td>");
                    table.append("<td style = 'border:1px solid #ddd;padding:8px;'>").append(expense.getAmount()).append("</td>");
                    table.append("<td style = 'border:1px solid #ddd;padding:8px;'>").append(expense.getCategoryId() != null ? expense.getCategoryName():"N/A").append("</td>");
                    table.append("</tr>");
                }
                table.append("</table>");
                String body = "Hi "+profile.getFullName()+",<br/><br/> Here is a summary of your expenses for today:<br/><br/> " +table+"<br/><br/>Best regards, <br/> Money Manager Team";
                emailService.sendEmail(profile.getEmail(), "Your daily expense summary", body);
            }
        }
        log.info("Job completed: sendDailyExpenseSummary()");
    }
}
