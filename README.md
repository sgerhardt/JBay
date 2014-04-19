JBay
====

Java GUI for the eBay Finding API - Find items on eBay with a search string


To use this software you will need the following:
1. eBay API key
2. The following eBay Finding Kit jars: 
    -finding.jar
    -log4j-1.2.16.jar

These items can be obtained by enrolling in the eBay developers program.

Import the project into Eclipse and add the jars to the project's build path.

Once the jars are added to your Java Build Path, modify the applicationId variable to hold your API key found at JBay\src\Models\EbayCommonModel.java


TODO:
1. Add ability to request a set number of results
2. Add category search
3. Add Subject-Observer pattern for live model + view searching
4. Make table resize it's width to hold maximum title and price String lengths
