package de.hybris.platform.customerreview.utils;

import java.util.Collections;
import java.util.List;
import de.hybris.platform.servicelayer.search.FlexibleSearchQuery;
import de.hybris.platform.servicelayer.search.FlexibleSearchService;
import de.hybris.platform.customerreview.model.CustomerReviewModel;
import de.hybris.platform.servicelayer.search.SearchResult; 
import de.hybris.platform.core.model.c2l.LanguageModel;
import de.hybris.platform.core.model.product.ProductModel;
import org.apache.commons.lang.StringUtils;
import de.hybris.platform.jalo.JaloBusinessException;

/**
 * This class reads list of curse words from a properties file which is loaded via bean definition.
 * 
 * The two methods in this class handle reviewing the comments and rating and return true if valid and
 * the other method retreives list of product's total number of customer reviews whose ratings are within a given range (inclusive).
 * 
 * @author bharatsurana
 *
 */
public class CustomerReviewUtils {

    /** The property config. */
    private static PropertiesConfiguration propertyConfig;
    private static List<String> curseWordsList;
	
	/*
	 * read a list of curse words.
	 * 
	 * @param rating - user rating for a product.
	 * @param headline and comment - are string values for product review
	 * 
	 * @throw
	 */
    
    /**
     * Check if Customer’s comment contains any of these curse words. If it does, throw an exception with a message.
     * Check if the rating is not < 0.  If it is < 0, throw an exception with a message
     * 
     * @param rating - user rating for a product
     * @param headline - are string values for product review
     * @param comment - are string values for product review
     * @return true or false for valid review
     * @throws JaloBusinessException
     */
	private boolean validateReviewAndRatirng (Double rating, String headline, String comment) throws JaloBusinessException
	{
		List<String> listCurseWords = getcurseWordsList();
		boolean valid = true;
		if(!headline.isEmpty() && !comment.isEmpty() && rating != null){
			for (String word : listCurseWords) 
			{
		       if( StringUtils.containsIgnoreCase(headline,word) || StringUtils.containsIgnoreCase(comment,word) )
		       {
		    	   valid = false;
		    	   throw new JaloBusinessException("Please enter a valid review. Plesae do not use any bad language");
		       }
			}
			if(rating < 0)
			{
				valid = false;
				throw new JaloBusinessException("Please enter a rating greater than or equal to 0.");
			}
		}
		else {
			valid = false;
			throw new JaloBusinessException("Headline, comment and or rating cannot be empty.");
		}
		return valid;
	}
	
	
	/**
	 * Method evaluates product’s total number of customer reviews whose ratings are within a given range (inclusive).
	 * 
	 * @param product = ProductModel,
	 * @param language - LanguageModel
	 * @param ratingMin - minimum rating to match
	 * @param ratingMax - maximum rating to match
	 * 
	 * @return searchResult - List of all product reviews that are within the ratings range.
	 */
	public List<CustomerReviewModel> getReviewsForProductAndLanguage(ProductModel product, LanguageModel language, Double ratingMin, Double ratingMax )
	{
	  // I have written a query similar to those from DefaultCustomerReviewDao.java
	  String query = "SELECT {" + Item.PK + "} FROM {" + "CustomerReview" + "} WHERE {" + "product" + "}=?product AND {" + "language" + "}=?language AND {" + "rating" + "} BETWEEN ?ratingMin AND ?ratingMax ORDER BY {" + "creationtime" + "} DESC";
	  FlexibleSearchQuery fsQuery = new FlexibleSearchQuery(query);
	  fsQuery.addQueryParameter("product", product);
	  fsQuery.addQueryParameter("language", language);
	  fsQuery.addQueryParameter("ratingMin", ratingMin);
	  fsQuery.addQueryParameter("ratingMax", ratingMax);
	  fsQuery.setResultClassList(Collections.singletonList(CustomerReviewModel.class));
	  
	  SearchResult<CustomerReviewModel> searchResult = getFlexibleSearchService().search(fsQuery);
	  return searchResult.getResult();
	} 
	
	/**
     * Setter method for properties configuration.
     *
     * @param propertyConfig
     *            the propertyConfig to set
     */
    public final void setPropertyConfig(
            final PropertiesConfiguration propertyConfig) {
        PCTMailGenerator.propertyConfig = propertyConfig;
    }
    
    /**
     * @return the curseWordsList
     */
    public static List<String> getcurseWordsList() {
        return propertyConfig.getString(curseWordsList);
    }

    /**
     * @param curseWordsList -
     *  the curseWordsList to set
     */
    public void setcurseWordsList(List<String> curseWordsList) {
    	CustomerReviewUtils.curseWordsList = curseWordsList;
    }
	
}
