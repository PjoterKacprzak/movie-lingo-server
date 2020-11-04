package com.example.movielingo.controller;

import org.simplejavamail.api.email.CalendarMethod;
import org.simplejavamail.api.email.Email;
import org.simplejavamail.api.mailer.Mailer;
import org.simplejavamail.api.mailer.config.TransportStrategy;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.MailerBuilder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;

@Controller
public class EmailService {




    static void sendVerificationMail(String email,String emailUsername,String emailPassword)
    {

        Email emailTemplate = EmailBuilder.startingBlank()
                .to("NickName", email)
                .withSubject("MovieLingo account activation")
                .from("Me","no-reply@movielingo.ct8.pl")
                .withHTMLText(EmailService.emailTemplate(email))
                //.withPlainText("Please view this email in a modern email client!")
                .withHeader("X-Priority", 1)
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer("s1.ct8.pl", 587, emailUsername, emailPassword)
                .withTransportStrategy(TransportStrategy.SMTP_TLS)
                .clearEmailAddressCriteria()
                .withProperty("mail.smtp.sendpartial", true)
                .withDebugLogging(true)
                .async()
                .buildMailer();
             mailer.sendMail(emailTemplate);
    }

    //TODO:Read that string from file
    public static String emailTemplate(String email)
    {
        String template = "<!DOCTYPE html>\n" +
                "<!-- saved from url=(0127)http://localhost:63342/pubspec.yaml/movie-lingo/templates/base_email_verification_template.html?_ijt=thbs337ulkte7ae10fqe1236ov -->\n" +
                "<html lang=\"en\" xmlns=\"http://www.w3.org/1999/xhtml\" xmlns:v=\"urn:schemas-microsoft-com:vml\" xmlns:o=\"urn:schemas-microsoft-com:office:office\"><head><meta http-equiv=\"Content-Type\" content=\"text/html; charset=UTF-8\">\n" +
                "    <!-- utf-8 works for most cases -->\n" +
                "    <meta name=\"viewport\" content=\"width=device-width\"> <!-- Forcing initial-scale shouldn't be necessary -->\n" +
                "    <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\"> <!-- Use the latest (edge) version of IE rendering engine -->\n" +
                "    <meta name=\"x-apple-disable-message-reformatting\">  <!-- Disable auto-scale in iOS 10 Mail entirely -->\n" +
                "    <meta name=\"format-detection\" content=\"telephone=no,address=no,email=no,date=no,url=no\"> <!-- Tell iOS not to automatically link certain text strings. -->\n" +
                "    <meta name=\"color-scheme\" content=\"light\">\n" +
                "    <meta name=\"supported-color-schemes\" content=\"light\">\n" +
                "    <title></title> <!-- The title tag shows in email notifications, like Android 4.4. -->\n" +
                "\n" +
                "    <!-- What it does: Makes background images in 72ppi Outlook render at correct size. -->\n" +
                "    <!--[if gte mso 9]>\n" +
                "    <xml>\n" +
                "        <o:OfficeDocumentSettings>\n" +
                "            <o:AllowPNG/>\n" +
                "            <o:PixelsPerInch>96</o:PixelsPerInch>\n" +
                "        </o:OfficeDocumentSettings>\n" +
                "    </xml>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "    <!-- Web Font / @font-face : BEGIN -->\n" +
                "    <!-- NOTE: If web fonts are not required, lines 23 - 41 can be safely removed. -->\n" +
                "\n" +
                "    <!-- Desktop Outlook chokes on web font references and defaults to Times New Roman, so we force a safe fallback font. -->\n" +
                "    <!--[if mso]>\n" +
                "    <style>\n" +
                "        * {\n" +
                "            font-family: sans-serif !important;\n" +
                "        }\n" +
                "    </style>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "    <!-- All other clients get the webfont reference; some will render the font and others will silently fail to the fallbacks. More on that here: http://stylecampaign.com/blog/2015/02/webfont-support-in-email/ -->\n" +
                "    <!--[if !mso]><!-->\n" +
                "    <!-- insert web font reference, eg: <link href='https://fonts.googleapis.com/css?family=Roboto:400,700' rel='stylesheet' type='text/css'> -->\n" +
                "    <!--<![endif]-->\n" +
                "\n" +
                "    <!-- Web Font / @font-face : END -->\n" +
                "\n" +
                "    <!-- CSS Reset : BEGIN -->\n" +
                "    <style>\n" +
                "\n" +
                "        /* What it does: Tells the email client that only light styles are provided but the client can transform them to dark. A duplicate of meta color-scheme meta tag above. */\n" +
                "        :root {\n" +
                "            color-scheme: light;\n" +
                "            supported-color-schemes: light;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Remove spaces around the email design added by some email clients. */\n" +
                "        /* Beware: It can remove the padding / margin and add a background color to the compose a reply window. */\n" +
                "        html,\n" +
                "        body {\n" +
                "            margin: 0 auto !important;\n" +
                "            padding: 0 !important;\n" +
                "            height: 100% !important;\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Stops email clients resizing small text. */\n" +
                "        * {\n" +
                "            -ms-text-size-adjust: 100%;\n" +
                "            -webkit-text-size-adjust: 100%;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Centers email on Android 4.4 */\n" +
                "        div[style*=\"margin: 16px 0\"] {\n" +
                "            margin: 0 !important;\n" +
                "        }\n" +
                "        /* What it does: forces Samsung Android mail clients to use the entire viewport */\n" +
                "        #MessageViewBody, #MessageWebViewDiv{\n" +
                "            width: 100% !important;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Stops Outlook from adding extra spacing to tables. */\n" +
                "        table,\n" +
                "        td {\n" +
                "            mso-table-lspace: 0pt !important;\n" +
                "            mso-table-rspace: 0pt !important;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Fixes webkit padding issue. */\n" +
                "        table {\n" +
                "            border-spacing: 0 !important;\n" +
                "            border-collapse: collapse !important;\n" +
                "            table-layout: fixed !important;\n" +
                "            margin: 0 auto !important;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Uses a better rendering method when resizing images in IE. */\n" +
                "        img {\n" +
                "            -ms-interpolation-mode:bicubic;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Prevents Windows 10 Mail from underlining links despite inline CSS. Styles for underlined links should be inline. */\n" +
                "        a {\n" +
                "            text-decoration: none;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: A work-around for email clients meddling in triggered links. */\n" +
                "        a[x-apple-data-detectors],  /* iOS */\n" +
                "        .unstyle-auto-detected-links a,\n" +
                "        .aBn {\n" +
                "            border-bottom: 0 !important;\n" +
                "            cursor: default !important;\n" +
                "            color: inherit !important;\n" +
                "            text-decoration: none !important;\n" +
                "            font-size: inherit !important;\n" +
                "            font-family: inherit !important;\n" +
                "            font-weight: inherit !important;\n" +
                "            line-height: inherit !important;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Prevents Gmail from changing the text color in conversation threads. */\n" +
                "        .im {\n" +
                "            color: inherit !important;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Prevents Gmail from displaying a download button on large, non-linked images. */\n" +
                "        .a6S {\n" +
                "            display: none !important;\n" +
                "            opacity: 0.01 !important;\n" +
                "        }\n" +
                "        /* If the above doesn't work, add a .g-img class to any image in question. */\n" +
                "        img.g-img + div {\n" +
                "            display: none !important;\n" +
                "        }\n" +
                "\n" +
                "        /* What it does: Removes right gutter in Gmail iOS app: https://github.com/TedGoas/Cerberus/issues/89  */\n" +
                "        /* Create one of these media queries for each additional viewport size you'd like to fix */\n" +
                "\n" +
                "        /* iPhone 4, 4S, 5, 5S, 5C, and 5SE */\n" +
                "        @media only screen and (min-device-width: 320px) and (max-device-width: 374px) {\n" +
                "            u ~ div .email-container {\n" +
                "                min-width: 320px !important;\n" +
                "            }\n" +
                "        }\n" +
                "        /* iPhone 6, 6S, 7, 8, and X */\n" +
                "        @media only screen and (min-device-width: 375px) and (max-device-width: 413px) {\n" +
                "            u ~ div .email-container {\n" +
                "                min-width: 375px !important;\n" +
                "            }\n" +
                "        }\n" +
                "        /* iPhone 6+, 7+, and 8+ */\n" +
                "        @media only screen and (min-device-width: 414px) {\n" +
                "            u ~ div .email-container {\n" +
                "                min-width: 414px !important;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "    <!-- CSS Reset : END -->\n" +
                "\n" +
                "    <!-- Progressive Enhancements : BEGIN -->\n" +
                "    <style>\n" +
                "\n" +
                "        /* What it does: Hover styles for buttons */\n" +
                "        .button-td,\n" +
                "        .button-a {\n" +
                "            transition: all 100ms ease-in;\n" +
                "        }\n" +
                "        .button-td-primary:hover,\n" +
                "        .button-a-primary:hover {\n" +
                "            background: #555555 !important;\n" +
                "            border-color: #555555 !important;\n" +
                "        }\n" +
                "\n" +
                "        /* Media Queries */\n" +
                "        @media screen and (max-width: 480px) {\n" +
                "\n" +
                "            /* What it does: Forces table cells into full-width rows. */\n" +
                "            .stack-column,\n" +
                "            .stack-column-center {\n" +
                "                display: block !important;\n" +
                "                width: 100% !important;\n" +
                "                max-width: 100% !important;\n" +
                "                direction: ltr !important;\n" +
                "            }\n" +
                "            /* And center justify these ones. */\n" +
                "            .stack-column-center {\n" +
                "                text-align: center !important;\n" +
                "            }\n" +
                "\n" +
                "            /* What it does: Generic utility class for centering. Useful for images, buttons, and nested tables. */\n" +
                "            .center-on-narrow {\n" +
                "                text-align: center !important;\n" +
                "                display: block !important;\n" +
                "                margin-left: auto !important;\n" +
                "                margin-right: auto !important;\n" +
                "                float: none !important;\n" +
                "            }\n" +
                "            table.center-on-narrow {\n" +
                "                display: inline-block !important;\n" +
                "            }\n" +
                "\n" +
                "            /* What it does: Adjust typography on small screens to improve readability */\n" +
                "            .email-container p {\n" +
                "                font-size: 17px !important;\n" +
                "            }\n" +
                "        }\n" +
                "\n" +
                "    </style>\n" +
                "    <!-- Progressive Enhancements : END -->\n" +
                "\n" +
                "</head>\n" +
                "<!--\n" +
                "\tThe email background color (#222222) is defined in three places:\n" +
                "\t1. body tag: for most email clients\n" +
                "\t2. center tag: for Gmail and Inbox mobile apps and web versions of Gmail, GSuite, Inbox, Yahoo, AOL, Libero, Comcast, freenet, Mail.ru, Orange.fr\n" +
                "\t3. mso conditional: For Windows 10 Mail\n" +
                "-->\n" +
                "<body width=\"100%\" style=\"margin: 0; padding: 0 !important; mso-line-height-rule: exactly; background-color: #222222;\">\n" +
                "<center role=\"article\" aria-roledescription=\"email\" lang=\"en\" style=\"width: 100%; background-color: #222222;\">\n" +
                "    <!--[if mso | IE]>\n" +
                "    <table role=\"presentation\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"100%\" style=\"background-color: #222222;\">\n" +
                "        <tr>\n" +
                "            <td>\n" +
                "    <![endif]-->\n" +
                "\n" +
                "    <!-- Visually Hidden Preheader Text : BEGIN -->\n" +
                "    <div style=\"max-height:0; overflow:hidden; mso-hide:all;\" aria-hidden=\"true\">\n" +
                "        (Optional) This text will appear in the inbox preview, but not the email body. It can be used to supplement the email subject line or even summarize the email's contents. Extended text preheaders (~490 characters) seems like a better UX for anyone using a screenreader or voice-command apps like Siri to dictate the contents of an email. If this text is not included, email clients will automatically populate it using the text (including image alt text) at the start of the email's body.\n" +
                "    </div>\n" +
                "    <!-- Visually Hidden Preheader Text : END -->\n" +
                "\n" +
                "    <!-- Create white space after the desired preview text so email clients don’t pull other distracting text into the inbox preview. Extend as necessary. -->\n" +
                "    <!-- Preview Text Spacing Hack : BEGIN -->\n" +
                "    <div style=\"display: none; font-size: 1px; line-height: 1px; max-height: 0px; max-width: 0px; opacity: 0; overflow: hidden; mso-hide: all; font-family: sans-serif;\">\n" +
                "        \u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\u200C&nbsp;\n" +
                "    </div>\n" +
                "    <!-- Preview Text Spacing Hack : END -->\n" +
                "\n" +
                "    <!--\n" +
                "        Set the email width. Defined in two places:\n" +
                "        1. max-width for all clients except Desktop Windows Outlook, allowing the email to squish on narrow but never go wider than 680px.\n" +
                "        2. MSO tags for Desktop Windows Outlook enforce a 680px width.\n" +
                "        Note: The Fluid and Responsive templates have a different width (600px). The hybrid grid is more \"fragile\", and I've found that 680px is a good width. Change with caution.\n" +
                "    -->\n" +
                "    <div style=\"max-width: 680px; margin: 0 auto;\" class=\"email-container\">\n" +
                "        <!--[if mso]>\n" +
                "        <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"680\">\n" +
                "            <tr>\n" +
                "                <td>\n" +
                "        <![endif]-->\n" +
                "\n" +
                "        <!-- Email Body : BEGIN -->\n" +
                "        <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" style=\"margin: auto;\">\n" +
                "            <!-- Email Header : BEGIN -->\n" +
                "            <tbody><tr>\n" +
                "                <td style=\"padding: 20px 0; text-align: center\">\n" +
                "\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <!-- Email Header : END -->\n" +
                "\n" +
                "            <!-- Hero Image, Flush : BEGIN -->\n" +
                "            <tr>\n" +
                "                <td style=\"background-color: #ffffff;\">\n" +
                "                    <img src=\"https://images.pexels.com/photos/128299/pexels-photo-128299.jpeg?cs=srgb&dl=pexels-mabel-amber-128299.jpg&fm=jpg\" width=\"680\" height=\"\" alt=\"alt_text\" border=\"0\" style=\"width: 100%; max-width: 680px; height: auto; background: #dddddd; font-family: sans-serif; font-size: 15px; line-height: 15px; color: #555555; margin: auto; display: block;\" class=\"g-img\">\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <!-- Hero Image, Flush : END -->\n" +
                "\n" +
                "            <!-- 1 Column Text + Button : BEGIN -->\n" +
                "            <tr>\n" +
                "                <td style=\"background-color: #ffffff;\">\n" +
                "                    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n" +
                "                        <tbody><tr>\n" +
                "                            <td style=\"padding: 20px; font-family: sans-serif; font-size: 15px; line-height: 20px; color: #555555;\">\n" +
                "                                <h1 style=\"margin: 0 0 10px; font-size: 25px; line-height: 30px; color: #333333; font-weight: normal;\">Praesent laoreet malesuada&nbsp;cursus.</h1>\n" +
                "                                <p style=\"margin: 0 0 10px;\">Maecenas sed ante pellentesque, posuere leo id, eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent laoreet malesuada cursus. Maecenas scelerisque congue eros eu posuere. Praesent in felis ut velit pretium lobortis rhoncus ut&nbsp;erat.</p>\n" +
                "                                <ul style=\"padding: 0; margin: 0; list-style-type: disc;\">\n" +
                "                                    <li style=\"margin:0 0 10px 30px;\" class=\"list-item-first\">A list item.</li>\n" +
                "                                    <li style=\"margin:0 0 10px 30px;\">Another list item here.</li>\n" +
                "                                    <li style=\"margin: 0 0 0 30px;\" class=\"list-item-last\">Everyone gets a list item, list items for everyone!</li>\n" +
                "                                </ul>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        <tr>\n" +
                "                            <td style=\"padding: 0 20px 20px;\">\n" +
                "                                <!-- Button : BEGIN -->\n" +
                "                                <table align=\"center\" role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" style=\"margin: auto;\">\n" +
                "                                    <tbody><tr>\n" +
                "                                        <td class=\"button-td button-td-primary\" style=\"border-radius: 4px; background: #222222;\">\n" +
                "                                            <a class=\"button-a button-a-primary\" href=\"localhost:8080/account-confirmation/"+email+"\" style=\"background: #222222; border: 1px solid #000000; font-family: sans-serif; font-size: 15px; line-height: 15px; text-decoration: none; padding: 13px 17px; color: #ffffff; display: block; border-radius: 4px;\">Centered Primary Button</a>\n" +
                "                                        </td>\n" +
                "                                    </tr>\n" +
                "                                    </tbody></table>\n" +
                "                                <!-- Button : END -->\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "\n" +
                "                        </tbody></table>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <!-- 1 Column Text + Button : END -->\n" +
                "\n" +
                "            <!-- Background Image with Text : BEGIN -->\n" +
                "\n" +
                "            <!-- Background Image with Text : END -->\n" +
                "\n" +
                "            <!-- 2 Even Columns : BEGIN -->\n" +
                "\n" +
                "            <!-- 2 Even Columns : END -->\n" +
                "\n" +
                "            <!-- 3 Even Columns : BEGIN -->\n" +
                "\n" +
                "            <!-- 3 Even Columns : END -->\n" +
                "\n" +
                "            <!-- Thumbnail Left, Text Right : BEGIN -->\n" +
                "\n" +
                "            <!-- Thumbnail Left, Text Right : END -->\n" +
                "\n" +
                "            <!-- Thumbnail Right, Text Left : BEGIN -->\n" +
                "\n" +
                "            <!-- Thumbnail Right, Text Left : END -->\n" +
                "\n" +
                "            <!-- Clear Spacer : BEGIN -->\n" +
                "            <tr>\n" +
                "                <td aria-hidden=\"true\" height=\"40\" style=\"font-size: 0px; line-height: 0px;\">\n" +
                "                    &nbsp;\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            <!-- Clear Spacer : END -->\n" +
                "\n" +
                "            <!-- 1 Column Text : BEGIN -->\n" +
                "\n" +
                "            <!-- 1 Column Text : END -->\n" +
                "\n" +
                "            </tbody></table>\n" +
                "        <!-- Email Body : END -->\n" +
                "\n" +
                "        <!-- Email Footer : BEGIN -->\n" +
                "        <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" style=\"max-width: 680px;\">\n" +
                "            <tbody><tr>\n" +
                "                <td style=\"padding: 20px; font-family: sans-serif; font-size: 12px; line-height: 15px; text-align: center; color: #ffffff;\">\n" +
                "                    <webversion style=\"color: #ffffff; text-decoration: underline; font-weight: bold;\">View as a Web Page</webversion>\n" +
                "                    <br><br>\n" +
                "                    Company Name<br><span class=\"unstyle-auto-detected-links\">123 Fake Street, SpringField, OR, 97477 US<br>(123) 456-7890</span>\n" +
                "                    <br><br>\n" +
                "                    <unsubscribe style=\"color: #ffffff; text-decoration: underline;\">unsubscribe</unsubscribe>\n" +
                "                </td>\n" +
                "            </tr>\n" +
                "            </tbody></table>\n" +
                "        <!-- Email Footer : END -->\n" +
                "\n" +
                "        <!--[if mso]>\n" +
                "        </td>\n" +
                "        </tr>\n" +
                "        </table>\n" +
                "        <![endif]-->\n" +
                "    </div>\n" +
                "\n" +
                "    <!-- Full Bleed Background Section : BEGIN -->\n" +
                "    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\" style=\"background-color: #709f2b;\">\n" +
                "        <tbody><tr>\n" +
                "            <td>\n" +
                "                <div align=\"center\" style=\"max-width: 680px; margin: auto;\" class=\"email-container\">\n" +
                "                    <!--[if mso]>\n" +
                "                    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"680\" align=\"center\">\n" +
                "                        <tr>\n" +
                "                            <td>\n" +
                "                    <![endif]-->\n" +
                "                    <table role=\"presentation\" cellspacing=\"0\" cellpadding=\"0\" border=\"0\" width=\"100%\">\n" +
                "                        <tbody><tr>\n" +
                "                            <td style=\"padding: 20px; text-align: left; font-family: sans-serif; font-size: 15px; line-height: 20px; color: #ffffff;\">\n" +
                "                                <p style=\"margin: 0;\">Maecenas sed ante pellentesque, posuere leo id, eleifend dolor. Class aptent taciti sociosqu ad litora torquent per conubia nostra, per inceptos himenaeos. Praesent laoreet malesuada cursus. Maecenas scelerisque congue eros eu posuere. Praesent in felis ut velit pretium lobortis rhoncus ut&nbsp;erat.</p>\n" +
                "                            </td>\n" +
                "                        </tr>\n" +
                "                        </tbody></table>\n" +
                "                    <!--[if mso]>\n" +
                "                    </td>\n" +
                "                    </tr>\n" +
                "                    </table>\n" +
                "                    <![endif]-->\n" +
                "                </div>\n" +
                "            </td>\n" +
                "        </tr>\n" +
                "        </tbody></table>\n" +
                "    <!-- Full Bleed Background Section : END -->\n" +
                "\n" +
                "    <!--[if mso | IE]>\n" +
                "    </td>\n" +
                "    </tr>\n" +
                "    </table>\n" +
                "    <![endif]-->\n" +
                "</center>\n" +
                "\n" +
                "</body></html>";
       // return String.format(template,email);
        return template;
    }

}
