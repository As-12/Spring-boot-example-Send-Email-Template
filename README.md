# Spring Boot Examples

## Sending Email with Java using HTML template

An example Spring-boot project using Java Mail, Apache Velocity, and HTML template to generate a beautiful templated email. The project serves as a quick boilerplate to be used in other larger projects. Any contribution is welcome by opening a pull request.
The example configures the mailer to send email over Gmail's SMTP protocol. Users will need to authorize "less secure app" on the security setting of their google account. 

![Sample templated Image](https://user-images.githubusercontent.com/15963/29055956-8dcca38e-7bb4-11e7-8a86-7b056ebf673d.png)

## HTML Template

The HTML Template in this example is based from [this](https://github.com/leemunroe/responsive-html-email-template).

## Quick Start

1. This is a simple maven project you can import directly to your Java IDE of choice.
2. To run an example,
	- Change mailing recipient in `application.properties`
	- Update sender credentials over on `com.as12.config.EmailConfig`
		- For Gmail, Google now requires 2 factor-authentication and App password. See https://support.google.com/accounts/answer/185833
	- Run your application once the properties are configured
	- Navigate to `http://localhost:8080/test` to send an example email

# License

[MIT License](https://opensource.org/licenses/MIT)
