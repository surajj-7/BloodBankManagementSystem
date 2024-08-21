package com.app.Services; 
 
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.Repository.DonorRepository;
import com.app.Repository.InventoryRepository;
import com.app.Repository.RequestingBloodRepository;
import com.app.entity.Donor;
import com.app.entity.Inventory;
import com.app.entity.Requesting; 
 
@Service 
public class DonorService  
{ 
 @Autowired 
 private DonorRepository donorRepository; 
 
 @Autowired
 private InventoryRepository inventoryRepository;
  
 @Autowired 
 private RequestingBloodRepository requestingBloodRepository; 
  
 public Donor saveDonor(Donor donor) 
 { 
  return donorRepository.save(donor); 
 } 
  
 public Donor saveUserAsDonor(Donor donor) {
	    // Find the corresponding inventory entry based on the donor's blood group
	    Inventory inventory = inventoryRepository.findByBloodGroup(donor.getBloodGroup());
	    
	    if(donor.getStatus()== null) {
	    	donor.setStatus("pending");
	    }

	    if (inventory != null) {
	        // Set the inventory reference in the donor entity
	        donor.setInventory(inventory);

	        
	    }

	    // Save the donor to the database
	    Donor savedDonor = donorRepository.save(donor);

	    // Return the saved donor
	    return savedDonor;
	}


  
 public Requesting saveBloodRequest(Requesting request) 
 { 
	 
	 Inventory inventory = inventoryRepository.findByBloodGroup(request.getBloodgroup());
	 
	 if (request.getStatus() == null) {
         request.setStatus("pending");
     }
	 if (inventory != null) {
	        // Set the inventory reference in the donor entity
	        request.setInventory(inventory);
	 }
  return requestingBloodRepository.save(request); 
 } 
  
// public Donor fetchDonorByBloodGroup(String bloodGroup) 
// { 
//  return donorRepository.countByBloodGroup(bloodGroup); 
// } 
  
 public void updateStatus(String email,int id) 
 { 
    requestingBloodRepository.updateStatus(email,id); 
    System.out.println("Updated"); 
 } 
 
 public void rejectStatus(String email,int id) 
 { 
    requestingBloodRepository.rejectStatus(email,id); 
 } 
 
 
 public void acceptStatusOfDonor(String email,int id) 
 { 
     donorRepository.acceptStatus(email,id); 
     System.out.println("Updated"); 
 } 
 
 public void rejectStatusOfDonor(String email,int id) 
 { 
     donorRepository.rejectStatus(email,id); 
 } 
 
 public List<Inventory> getInventoryWithDonorCounts() {
     List<Inventory> inventories = inventoryRepository.findAll();
     for (Inventory inventory : inventories) {
         int count =inventoryRepository.countByBloodGroupIgnoreCase(inventory.getBloodGroup());
         inventory.setDonorCount(count);  // Make sure the setter method exists
     }
     return inventories;
 }
  
 

 public void deleteDonor(int id) {
     // Retrieve the donor before deletion
//     Donor donor = donorRepository.findById(id)
//                                   .orElseThrow(() -> new RuntimeException("Donor not found with id: " + id));
//
//     // Get the corresponding inventory entry
//     Inventory inventory = inventoryRepository.findByBloodGroup(donor.getBloodGroup());
//
//     if (inventory != null) {
//         int updatedUnits = inventory.getTotalUnits() - donor.getUnits();
//         
//         // Ensure the totalUnits do not go negative
//         if (updatedUnits >= 0) {
//             inventory.setTotalUnits(updatedUnits);
//             inventoryRepository.save(inventory);
//         } else {
//             throw new RuntimeException("Cannot delete donor: inventory units would become negative.");
//         }
//     }

     // Delete the donor
     donorRepository.deleteById(id);
     System.out.println("Deleted Successfully!");
 }


  
 public Donor fetchDonorByGender(String gender) 
 { 
  return donorRepository.findByGender(gender); 
 } 
 
 public List<Donor> getAllDonors()  
 { 
  return (List<Donor>)donorRepository.findAll(); 
 } 
 
 public Integer countAcceptedDonations(String email) {
     return requestingBloodRepository.sumUnitsByEmailAndStatusIgnoreCase(email, "accept");
 }
  
 public List<Requesting> getRequestHistory()  
 { 
  return (List<Requesting>)requestingBloodRepository.findAll(); 
 } 
 public List<Donor> getdonorRequestHistory()  
 { 
  return (List<Donor>)donorRepository.findAll(); 
 } 
  
 public List<Requesting> getRequestHistoryByEmail(String email)  
 { 
  return (List<Requesting>)requestingBloodRepository.findByEmail(email); 
 } 
 
 public List<Donor> getDonorRequestHistoryByEmail(String email)  
 { 
  return (List<Donor>)donorRepository.findByEmail(email); 
 } 
  
 public List<Donor> getBloodDetails() 
 { 
  return (List<Donor>)donorRepository.findBloodDetails(); 
 } 
 
 public boolean isAvailable(String bloodGroup, Integer units) {
     int availableUnits = donorRepository.getAvailableUnitsByBloodGroup(bloodGroup);
     return availableUnits >= units;
 }
  
 public void checkforOldBloodSamples(List<Donor> donors) {
	    LocalDate todayDate = LocalDate.now();
	    
	    for (Donor donor : donors) {
	        LocalDate lastDonationDate = donor.getLastDonationDate();
	        long days = findDifference(lastDonationDate, todayDate);
	        
	        if (days > 90) {
	            String userName = donor.getName();
	            donorRepository.deleteByUsername(userName);
	        }
	    }
	}

	static long findDifference(LocalDate lastDonationDate, LocalDate todayDate) {
	    long daysDifference = ChronoUnit.DAYS.between(lastDonationDate, todayDate);
	    System.out.println("The Blood sample is " + daysDifference + " days older.");
	    return daysDifference;
	}
}