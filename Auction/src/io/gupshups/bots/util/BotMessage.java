package io.gupshups.bots.util;

public class BotMessage {
public static class MSG{
	public static final String WELCOME="welcome to auction bot\nIf you are seller type 'seller'."+
"\nIf you are bidder type 'bid'.\nFor more info type 'help'. ";
	public static final String SELLER_NAME="Please enter your name.";
	public static final String PRODUCT_DETAILS="Enter your product details";
	public static final String BASE_PRICE="Enter the start(base) price for your product";
	public static final String ACCOUNT_NO="Enter your account number";
	public static final String IFSC_CODE="Enter your IFSC code";
	public static final String THANK_YOU="Thank you for entering your product details for bid.";
	public static final String ERROR="you already have a product for bid you cant add till it get sold.";
	//public static final String HELP="Sorry i didn't get you \nYou can type 'help' to get Help";
	public static final String STOP="You can sell your bid by typing 'stop product_id'.\nExample 'stop 2'.\nFor more info type 'help'.";
	public static final String PRODUCT_NOT_AVAILABLE="product is not available for bidding";
	public static final String RESTRICT="you are not allowed to bid for this product.";
	public static final String OWNER="You are not the owner of this product.";
	public static final String REMOVED = "Your product has been removed from the auction you can add another product.\nFor more info type 'help'.\nThank you for using Auctiob Bot";
	public static final String INCREASE_BID="Please increase your bid highest bid is Rs:";
	public static final String HIGHEST_BIDDER="you are the highest bidder currently your bid is ";
	public static final String AMOUNT="Enter valid amount.";
	public static final String BID="\n\nYou can bid by typing 'Bid product_id price'.\nExample: 'Bid 2 1200'.";
	public static final String HELP = "Hi, I am here to Help you.\n\nIf you want to sell something..Type 'seller'"+
			//"\nSeller can remove his product from bid without selling by typing 'remove product_id'.\nExample: 'remove 1'."+
	"\n\nIf you want to Place a Bid on a product..Type 'Bid' to see the product available for bidding"+
	"\nTo bid on product type 'bid product_id price'.\nExample: bid 2 1200"+
	"\n\nIf Seller wants to remove his product..Type 'stop product_id'.\nExample: stop 2"+
	"\nFor Eg. stop 1\n\nIf user wants to stop receiving notifications..Type 'leave product_id'.\nExample: 'leave 2'"+
	"\n\nIf the seller wants to sell his product to the highest bidder..Type 'stop product_id'.\nExample: stop 1";
	

}
}
