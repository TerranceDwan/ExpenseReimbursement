# ExpenseReimbursement

# Project Description

The Expense Reimbursement System (ERS) manages the process of reimbursing employees for expenses incurred while on company time. 
All employees can log in and submit requests for reimbursement and view their past tickets and pending requests. Managers can then 
log in and view all reimbursement requests and past history for all employees. These managers are authorized to approve and deny 
requests for expense reimbursements.

# Technologies Used

Built intuitive UI with HTML, CSS, and Javascript.
Styled page responsively with Bootstrap.
Retrieved data from API with AJAX requests.
Managed user sessions with HTTPSession technology.
Stored and manipulated data with JDBC.
Implemented the DAO design pattern to organize the data flow.
Used Front Controller design patter to organize endpoint access.
Tested logic and functionality with JUnit and Mockito.
Managed data with PostgreSQL in a RDS

# Features

All users can do the following things:
Sign-in.
Apply for reimbursement.
View past reibursement requests.
Edit personal information.

Only managers have the following abilities:
Approve or deny requests.
View all pending requests of subordinates.
View reimbursement history for all subordinates.

# Getting Started

In order to use this technology, you will need to create a config.properties file in the resources folder to replace the one that is 
included in the gitignore file.  This file should have your credentials to link to your database. 

# Contributors

Terrance Lewis
