<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>

<div class="body-text">Write your name in the right fields. Also write your imaginary card number. By clicking CCV field card will turn.</div>

  <form onsubmit="setTimeout(function(){alert('Your order was successfully placed. It is out for delivery and should arrive in 45 minutes or less. Thank you for shopping with ConcordiaEats.');}, 0);" action="../../../makeorder">
    <div class="form-container">
      <div class="personal-information">
        <h1>Payment Information</h1>
      </div> <!-- end of personal-information -->
           
          <input id="column-left" type="text" name="first-name" placeholder="First Name" required/>
          <input id="column-right" type="text" name="last-name" placeholder="Surname" required/>
          <input id="input-field" type="text" name="number" placeholder="Card Number" required pattern="^4[0-9]{12}(?:[0-9]{3})?$"/>
          <input id="column-left" type="text" name="expiry" required="required" placeholder="MM / YY" pattern="(0[1-9]|1[0-2])\/([0-9]{2})"/>
		  <input id="column-right" type="text" name="cvc" placeholder="CCV" required pattern="\d{3}"/>

         
          <div class="card-wrapper"></div>
      
          <input id="input-field" type="text" name="streetaddress" required="required" autocomplete="on" maxlength="45" placeholder="Streed Address"/>
          <input id="column-left" type="text" name="city" required="required" autocomplete="on" maxlength="20" placeholder="City"/>
          <input id="column-right" type="text" name="zipcode" required="required" autocomplete="on" pattern="[0-9]*" maxlength="5" placeholder="ZIP code"/>
          <input id="input-field" type="email" name="email" required="required" autocomplete="on" maxlength="40" placeholder="Email"/>
          <input id="input-button" type="submit" value="Submit"/>
        
    </form>
  </div>
  
<style>
@import url(https://fonts.googleapis.com/css?family=Roboto:400,900,700,500);

body {
  padding: 60px 0;
  background-color: rgba(200, 200, 200, 0.7);
  margin: 0 auto;
  width: 600px;
}
.body-text {
  padding: 0 20px 30px 20px;
  font-family: "Roboto";
  font-size: 1em;
  color: #333;
  text-align: center;
  line-height: 1.2em;
}
.form-container {
  flex-direction: column;
  justify-content: center;
  align-items: center;
}
.card-wrapper {
  background-color: #919191;
  width: 100%;
  display: flex;

}
.personal-information {
  background-color: #707070;
  color: #fff;
  padding: 1px 0;
  text-align: center;
}
h1 {
  font-size: 1.3em;
  font-family: "Roboto"
}
input {
  margin: 1px 0;
  padding-left: 3%;
  font-size: 14px;
}
input[type="text"]{
  display: block;
  height: 50px;
  width: 97%;
  border: none;
}
input[type="email"]{
  display: block;
  height: 50px;
  width: 97%;
  border: none;
}
input[type="submit"]{
  display: block;
  height: 60px;
  width: 100%;
  border: none;
  background-color: #707070;
  color: #fff;
  margin-top: 2px;
  curson: pointer;
  font-size: 0.9em;
  text-transform: uppercase;
  font-weight: bold;
  cursor: pointer;
}
input[type="submit"]:hover{
  background-color: #999999;
  transition: 0.3s ease;
}
#column-left {
  width: 46.5%;
  float: left;
  margin-bottom: 2px;
}
#column-right {
  width: 46.5%;
  float: right;
}

@media only screen and (max-width: 480px){
  body {
    width: 100%;
    margin: 0 auto;
  }
  .form-container {
    margin: 0 2%;
  }
  input {
    font-size: 1em;
  }
  #input-button {
    width: 100%;
  }
  #input-field {
    width: 96.5%;
  }
  h1 {
    font-size: 1.2em;
  }
  input {
    margin: 2px 0;
  }
  input[type="submit"]{
    height: 50px;
  }
  #column-left {
    width: 96.5%;
    display: block;
    float: none;
  }
  #column-right {
    width: 96.5%;
    display: block;
    float: none;
  }
}</style>
<script>
$('form').card({
    container: '.card-wrapper',
    width: 280,

    formSelectors: {
        nameInput: 'input[name="first-name"], input[name="last-name"]'
    }
});
</script>
</body>
</html>
