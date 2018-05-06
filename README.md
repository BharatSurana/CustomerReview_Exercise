**I have completed the task assigned for this code challenge.**

Created new Java class file to handle the below requirements - CustomerReviewUtils.java

**1. Provide a way to get a product’s total number of customer reviews whose ratings are within a given range (inclusive).**
    
Method getReviewsForProductAndLanguage() evaluates and returns product’s total number of customer reviews whose ratings are within a given range (inclusive).


**2.	Add the following additional checks before creating a customer review:**

**a.  Your service should read a list of curse words. This list should not be defined in Java class.** 

I have created a new config.properties file whichholds all the curse words. These are then injected via the bean.

**b. Check if Customer’s comment contains any of these curse words. If it does, throw an exception with a message.**

**c. Check if the rating is not < 0.  If it is < 0, throw an exception with a message.**
  
Method validateReviewAndRatirng() Checks if Customer’s comment contains any of these curse words. If it does, throw an exception with a message. And also, Checks if the rating is not < 0.  If it is < 0, throw an exception with a message
  
**3.	Ensure the new functionality can be used elsewhere in the application (i.e.  a bean containing the new functionality is defined within the customerreview-spring.xml).**

Updated customerreview-spring.xml with the following bean definition.

	<bean id="customerReviewUtils" class="de.hybris.platform.customerreview.utils.CustomerReviewUtils" scope="tenant">
    <property name="propertyConfig" alue="classpath:path/config.properties"/>
    <property name="curseWordsList" value="curse.words.list"/></bean>
