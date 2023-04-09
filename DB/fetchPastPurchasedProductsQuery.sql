show databases;
use springproject;

select * from purchase_details;
select * from product;


select p.id, p.name, p.description, p.imagePath, p.price, p.salesCount, p.isOnSale, p.discountPercent, c.id, c.name
from product p
join category c 
	on p.categoryid = c.id 
where p.id in (
			select distinct(productId) 
			from purchase_details
			where purchaseId in 
					(select pur.id 
					from purchase pur
					where userId=3)
			);
            
describe product;
                    
				

