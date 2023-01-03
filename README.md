# LIMS
Laboratory Information Management System (LIMS)

In this project a system for automation of calculations and database interactions for a common laboratory analysis was developed. The system is made up of three micro services; a User Interface (UI), a Camunda process for automation and a GraphQL API for interactions with a relational database. A user can select samples, start an analysis, select a raw data file from which the process will perform calculations and accept the result after review. The process interacts with the API to save data in the database. Camunda’s process modelling tool and process engine were used for automation. 

Project overview:

![overview](https://user-images.githubusercontent.com/71063958/192153984-8bd0a38d-3675-4eab-a72c-bd713303c943.JPG)

Camunda process:
![process](https://user-images.githubusercontent.com/71063958/192155013-8dd33490-3a36-48df-ae1f-a836437dffb7.png)


References for Camunda platform
- Documentation for Camunda:    https://docs.camunda.org/manual/7.16/
- Camunda Platform Initializr:  https://start.camunda.com/
