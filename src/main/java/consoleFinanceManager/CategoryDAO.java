package consoleFinanceManager;

import java.util.ArrayList;

public class CategoryDAO {
	
	private int nextID = 1;
		
	private ArrayList <Category> categoryList = new ArrayList <Category>();
	
	
	//add Category to the list
	public Category addCategory(Category aCategory) {
		
		//i ll set ID==nextID  and then nextID++ to be ready for next category
		if(aCategory.getID()==-1) {
			aCategory.setID(nextID);
			nextID++;
		} 
		
		categoryList.add(aCategory); 
		
		return aCategory;
	}
	
	public CategoryDAO() {
        loadDefaultCategories();
    }
	
	
	private void loadDefaultCategories() {
        // EXPENSE categories
        addCategory(new Category("Groceries", TransactionType.EXPENSE));
        addCategory(new Category("Utilities", TransactionType.EXPENSE));
        addCategory(new Category("Rent", TransactionType.EXPENSE));
        addCategory(new Category("Entertainment", TransactionType.EXPENSE));
        addCategory(new Category("Transportation", TransactionType.EXPENSE));
        addCategory(new Category("Healthcare", TransactionType.EXPENSE));
        addCategory(new Category("Clothing", TransactionType.EXPENSE));

        // INCOME categories
        addCategory(new Category("Salary", TransactionType.INCOME));
        addCategory(new Category("Bonus", TransactionType.INCOME));
        addCategory(new Category("Freelance", TransactionType.INCOME));
        addCategory(new Category("Investments", TransactionType.INCOME));
        addCategory(new Category("Gifts", TransactionType.INCOME));
    }
	
	
	
	
	public ArrayList<Category> getCategoryList() {
        return categoryList;
    }
	
	
	
	//get category given the id
	public Category findCategoryByID(int id) {
		
		for( Category category : categoryList) {
			if (category.getID()==id) {
				return category;
			}
		}
		return null; // if not found
	}
	
	//get category name given the id
	public String findCategoryNameByID(int id) {
			
		for( Category category : categoryList) {
			if (category.getID()==id) {
				return category.getName();
			}
		}
		return null; // if not found
	}
	
	
	
	
	public ArrayList<Category> findCategoriesByType(TransactionType type) {
		
        ArrayList<Category> filteredCategories = new ArrayList<>();
        
        for (Category category : categoryList) {
            if (category.getType() == type) {
                filteredCategories.add(category);
            }
        }
        return filteredCategories;
    }
	

}
