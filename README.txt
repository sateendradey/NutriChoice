Nutri-Recipe v0.2

*************************************************************************************
SUMMARY
*************************************************************************************
Nutrition isn’t always easy, but no one said it had to be complicated. Build your diet 
with fully customizable recipes, tweak based on detailed nutrition information that 
updates in real-time. We're focused on providing tools and support for people who want 
to take control over their nutrition. Given the saturation of information in the diet 
industry, we focus on more pragmatic elements of healthy eating such as planning, 
shopping and cooking.  This site is latest part of our ongoing project to make 
effective nutrition strategies available to everyone, especially people who are 
too busy to get started on their own. The target demographic for this application 
includes people of ages ranging from 18-100+. It can be used by college kids to eat 
healthy or by fitness freaks to plan their meals according to their regimen or by mid-
age and elderly people who have been advised to eat more a particular nutrient by 
their medical practitioner. The Nutri-recipe platform can be extended, in the future, 
to produce eating plans for specific medical needs as well 
(e.g. a diet for people on warfarin).

*************************************************************************************
CHANGES
*************************************************************************************
version 0.4
-Release Candidate
-Added validations on Age, Gender, Nutrient,and Threshold inputs
-Disabled driver logging in console
-Modularized the code
-Removed the dead code
-Removed unnecessary comments from the code
	

version 0.3
- beta release 
- increased search criteria from one nutrient to two
- moved recommended nutrient values for high, medium, and low thresholds to its own
  database collection instead of using hard-coded values
- modularized code has been implemented   
version 0.2
- alpha release
- completed first pass of primary use case (retrieve recipes based on simple inputs)
- added threshold for major nutrient group (calories, proteins, fats, sodium)
- added basic error handling for input
- disabled mongodb logging on console for cleaner output
version 0.1
- proof of architecture

*************************************************************************************
SETUP
*************************************************************************************
1. Local instance of mongoDB should be running
2. Set Java Home in Gradle Run Configuration to the Jave jdk file on your System 
(In some cases the tools.jar file cannot be found. To resolve this we need to set Java Home to the jdk file on the system)
Steps to do this :
a. Right click on the Gradle Run task in Gradle Tasks Tab and choose 'Open Gradle Run Configuration'
b. On the window that pops up , click on Java Home tab
c. Click on the browse button  on Java Home screen and select the jdk home path on your System 
d. Then click on 'Apply'
e. Then Click on 'Ok'
f. Then Run the Gradle Project    
*************************************************************************************
OTHER NOTES
*************************************************************************************
1. Enter input values as specified by the console prompts as only very basic error 
handling is in place 

