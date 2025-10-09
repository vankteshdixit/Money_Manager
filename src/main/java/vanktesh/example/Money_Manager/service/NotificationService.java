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
    // Utility method to escape HTML special characters in text
    private static String escapeHtml(String text) {
        if (text == null) return "";
        return text.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
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

                table.append("<div style='font-family:Arial,Helvetica,sans-serif;background:#f4f6f8;padding:18px;'>");
                table.append("  <div style='max-width:700px;margin:0 auto;padding:18px;background:#ffffff;border-radius:8px;box-shadow:0 4px 12px rgba(16,24,40,0.05);'>");

// header
                table.append("    <div style='display:flex;align-items:center;gap:12px;margin-bottom:14px;'>");
                table.append("      <div style='width:44px;height:44px;border-radius:8px;background:#2563eb;color:#fff;display:flex;align-items:center;justify-content:center;font-weight:700;'>MM</div>");
                table.append("      <div>");
                table.append("        <div style='font-size:16px;font-weight:700;color:#111827;'>Daily Expense Summary</div>");
                table.append("        <div style='font-size:13px;color:#6b7280;margin-top:2px;'>Summary for today</div>");
                table.append("      </div>");
                table.append("    </div>");

// intro
                table.append("    <p style='margin:6px 0 14px 0;color:#374151;font-size:14px;'>Hi " + escapeHtml(profile.getFullName()) + ",</p>");
                table.append("    <p style='margin:0 0 18px 0;color:#6b7280;font-size:13px;'>Here is a summary of your expenses for today.</p>");

// table wrapper (responsive)
                table.append("    <div style='overflow-x:auto;'>");
                table.append("      <table style='width:100%;border-collapse:collapse;table-layout:fixed;margin-top:12px;'>");

// correctly defined header - MATCHES the number of columns in rows (4)
                table.append("        <thead>");
                table.append("          <tr>");
                table.append("            <th style='text-align:left;padding:10px 12px;background:#f8fafc;color:#0f172a;font-weight:600;border-bottom:1px solid #e6eef8;width:6%;'>#</th>");
                table.append("            <th style='text-align:left;padding:10px 12px;background:#f8fafc;color:#0f172a;font-weight:600;border-bottom:1px solid #e6eef8;width:44%;'>Name</th>");
                table.append("            <th style='text-align:left;padding:10px 12px;background:#f8fafc;color:#0f172a;font-weight:600;border-bottom:1px solid #e6eef8;width:25%;'>Amount</th>");
                table.append("            <th style='text-align:left;padding:10px 12px;background:#f8fafc;color:#0f172a;font-weight:600;border-bottom:1px solid #e6eef8;width:25%;'>Category</th>");
                table.append("          </tr>");
                table.append("        </thead>");

// rows (logic EXACTLY the same as yours)
                table.append("        <tbody>");
                int i = 1;
                for (ExpenseDTO expense : todayExpenses) {
                    table.append("          <tr>");
                    table.append("            <td style='padding:10px 12px;border-bottom:1px solid #f1f5f9;vertical-align:middle;'>").append(i++).append("</td>");
                    table.append("            <td style='padding:10px 12px;border-bottom:1px solid #f1f5f9;vertical-align:middle;'>")
                            .append(escapeHtml(expense.getName())).append("</td>");
                    table.append("            <td style='padding:10px 12px;border-bottom:1px solid #f1f5f9;vertical-align:middle;font-weight:600;'>")
                            .append(expense.getAmount()).append("</td>");
                    table.append("            <td style='padding:10px 12px;border-bottom:1px solid #f1f5f9;vertical-align:middle;'>")
                            .append(expense.getCategoryId() != null ? escapeHtml(expense.getCategoryName()) : "N/A").append("</td>");
                    table.append("          </tr>");
                }
                table.append("        </tbody>");

                table.append("      </table>");
                table.append("    </div>"); // overflow-x

// footer
                table.append("    <div style='margin-top:16px;color:#6b7280;font-size:13px;'>");
                table.append("      <p style='margin:0;'>Best regards,<br/>Money Manager Team</p>");
                table.append("    </div>");

                table.append("  </div>"); // card
                table.append("</div>"); // container

                String body = "Hi " + profile.getFullName() + ",<br/><br/> Here is a summary of your expenses for today:<br/><br/> "
                        + table.toString() + "<br/><br/>Best regards, <br/> Money Manager Team";
            }
        }
        log.info("Job completed: sendDailyExpenseSummary()");
    }
}
