package idatt2105.project.backend.service;

import idatt2105.project.backend.model.Item;
import idatt2105.project.backend.model.Listing;
import idatt2105.project.backend.model.ShortenedListing;
import idatt2105.project.backend.repository.ItemRepository;
import idatt2105.project.backend.repository.ListingRepository;
import idatt2105.project.backend.util.TokenHandling;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ListingService {

  @Autowired
  ListingRepository listingRepository;

  @Autowired
  ItemRepository itemRepository;

  public Listing getListingByID(int itemID) throws RuntimeException {
    try {
      Listing listing = listingRepository.getListingByItemID(itemID);
      int userIDFromListing = itemRepository.getUserID(itemID);
      int userIDFromSecurityContext;

      //tries to get a userID from the security context if the request sent has a valid token
      //if it is not able to do so, the id from the security context will be set to -1
      try {
        userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();
      } catch (RuntimeException e) {
        userIDFromSecurityContext = -1;
      }

      //if the userID from the listing is the same as the userID from the security context / token,
      //this will indicate that the one requesting the item is the authorized owner
      if (userIDFromListing == userIDFromSecurityContext) {
        listing.setIsSeller(true);
      }

      return listing;
    } catch (Exception e) {
      throw new RuntimeException("An error occurred attempting to retrieve listing.");
    }
  }

  public List<ShortenedListing> getListingsFromUserPaginated(int pagesize, int page)
      throws RuntimeException {
    validatePagesizeAndPage(pagesize, page);
    int userID = TokenHandling.getIdFromSecurityContext();

    try {
      return listingRepository.getAllShortenedListingsForUserPaginated(userID, pagesize, page);
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to retrieve the listings from the user.");
    }
  }

  public List<ShortenedListing> getShortenedListingsPaginated(int pagesize, int page)
      throws RuntimeException {
    validatePagesizeAndPage(pagesize, page);
    try {
      return listingRepository.getShortenedListingsPaginated(pagesize, page);
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to retrieve listings.");
    }

  }

  public List<ShortenedListing> getShortenedListingsFromCategoryPaginated(int categID,
                                                                           int pagesize, int page)
      throws RuntimeException {
    validatePagesizeAndPage(pagesize, page);
    try {
      return listingRepository.getShortenedListingsFromCategoryPaginated(categID, pagesize, page);
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to retrieve listings.");
    }
  }

  //TODO: Do exception handling for the below check-methods
  public Boolean checkIfListingPageIncludesLastListing(List<ShortenedListing> listings, int pagesize,
                                                       int page)
      throws RuntimeException {
    try {
      int totalListingsDb = listingRepository.countListings();
      int totalListingsRetrieved = pagesize * (page - 1) + listings.size();

      if (totalListingsDb == totalListingsRetrieved) {
        return true;
      }
      return false;
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to determine if the listings sent contained the last listing.");
    }
  }

  public Boolean checkIfCategoryListingPageIncludesLastListing(int categID, List<ShortenedListing> listings,
                                                               int pagesize, int page)
      throws RuntimeException{
    try {
      int totalListingsDb = listingRepository.countListingsForCategory(categID);
      int totalListingsRetrieved = pagesize * (page - 1) + listings.size();

      if (totalListingsDb == totalListingsRetrieved) {
        return true;
      }
      return false;
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to determine if the listings sent contained the last listing.");
    }

  }

  public Boolean checkIfUserListingsPageIncludesLastListing(List<ShortenedListing> listings,
                                                            int pagesize, int page)
      throws RuntimeException {
    try {
      int userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();

      int totalListingsDb = listingRepository.countListingsFromUser(userIDFromSecurityContext);
      int totalListingsRetrieved = pagesize * (page - 1) + listings.size();

      if (totalListingsDb == totalListingsRetrieved) {
        return true;
      }
      return false;
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to determine if the listings sent contained the last listing.");
    }
  }

  private void validatePagesizeAndPage(int pagesize, int page) throws IllegalArgumentException {
    if (page < 1 || pagesize < 1) {
      throw new IllegalArgumentException("Invalid page and page size parameters.");
    }
  }
}
