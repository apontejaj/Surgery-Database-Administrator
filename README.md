# Surgery-Database-Administrator
Implementation of a GUI to administrate mySQL

The program is a solution for managing the patient records of a medical practice. It allows finding records of current patients and  allowing for a faster turnaround time with the billing for clients. 

# Account Types 
The system has three user account types. Depending on their account type, they are sent to a different dashboard where they can choose the task they wish to complete. 
 
 Receptionist – Mainly focused on making appointments in the system and taking different messages which have been left for the doctor.  
 Receptionist sample user: "michael"  Password: "mcgloin"
 
 Doctor – The doctor can view all of the messages which have been left for him, search for different patients in the database, and take notes about the current patient and make notes about the time they visited and details of the different medication which have been prescribed to them. 
 Doctor sample user: "amilcar"  Password: "aponte"
 
 Billing Department – This account type is only focused upon the billing aspects of the practice.
 Billing sample user: "kyle"  Password: "goslin"
 
 # mySQL Database
 
The document surgery.sql include the complete set of instructions to create a complete sample data base. The sample users have already been incluided in this database.

The program have been written using "root" as user for mySQL, and an empty password in the port 3306 of the local host.
