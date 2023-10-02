import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import Container from "react-bootstrap/Container";

const NavigationBar = (props) => {
  const handleLogout = () => {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    alert("Logged Out");
  };

  return (
    <Navbar
      className="m-0 py-3"
      collapseOnSelect
      expand="lg"
      bg="primary"
      variant="dark"
    >
      <Container fluid>
        <Navbar.Brand href="/welcome">NEX Food Ordering</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto">
            <Nav.Link></Nav.Link>
            <Nav.Link href="/welcome">Food List</Nav.Link>
            <Nav.Link href="/orderDetails">Orders</Nav.Link>
          </Nav>
          <Nav>
            <Nav.Link onClick={handleLogout} href="/">
              Logout
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavigationBar;
