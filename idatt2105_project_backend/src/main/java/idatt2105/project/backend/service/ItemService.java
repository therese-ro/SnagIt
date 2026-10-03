package idatt2105.project.backend.service;

import idatt2105.project.backend.model.Item;
import idatt2105.project.backend.model.Listing;
import idatt2105.project.backend.model.ShortenedListing;
import idatt2105.project.backend.repository.ItemRepository;
import idatt2105.project.backend.repository.ListingRepository;
import idatt2105.project.backend.util.TokenHandling;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;

@Service
public class ItemService {
  @Autowired
  ItemRepository itemRepository;

  @Autowired
  ListingRepository listingRepository;

  @Autowired
  ImageService imageService;

  //TODO: Add validation
  public void addNewListing(Item item) throws RuntimeException {
    try {
      int userID = TokenHandling.getIdFromSecurityContext();

      itemRepository.insertItem(item.getShortDesc(), item.getLongDesc(), item.getPrice(),
          item.getLongitude(), item.getLatitude(), item.getThumbnailPath(), item.getCategoryID(),
          userID);
    } catch (Exception e) {
      throw new RuntimeException("An error occurred trying to add a new listing");
    }
  }

  public void deleteListing(int itemID) throws RuntimeException {
    try {
      int userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();

      Listing listing = listingRepository.getListingByItemID(itemID);
      if (listing != null) {
        if (userIDFromSecurityContext == itemRepository.getUserID(itemID)) {
          itemRepository.deleteItem(itemID);
          imageService.deleteImage(listing.getThumbnailPath());
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("An error occurred while trying to delete an item.");
    }
  }

  public void updateListing(Item item) throws RuntimeException {
    try {
      Listing oldItem = listingRepository.getListingByItemID(item.getItemID());
      int userIDFromSecurityContext = TokenHandling.getIdFromSecurityContext();
      if (oldItem != null &&
          userIDFromSecurityContext == itemRepository.getUserID(item.getItemID())) {
        if (!oldItem.getThumbnailPath().equals(item.getThumbnailPath())) {
          imageService.deleteImage(oldItem.getThumbnailPath());
        }
        itemRepository.updateItem(item.getItemID(), item.getShortDesc(), item.getLongDesc(),
            item.getPrice(), item.getLongitude(), item.getLatitude(), item.getIsForSale(),
            item.getThumbnailPath(), item.getCategoryID());
      }
    } catch (Exception e) {
        throw new RuntimeException("An error occurred trying to update the item.");
      }
  }
}
