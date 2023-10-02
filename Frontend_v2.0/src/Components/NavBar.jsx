import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import Container from "react-bootstrap/Container";

const NavBar = () => {
  

  return (
    <Navbar
      className="m-0 py-3"
      collapseOnSelect
      expand="lg"
      bg="primary"
      variant="dark"
    >
      <Container fluid>
        <Navbar.Brand href="/">NEX Food Ordering</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav" />
        <Navbar.Collapse id="responsive-navbar-nav">
          <Nav className="me-auto"></Nav>
          
          <Nav.Link href="/admin">ADMIN</Nav.Link>
          <Nav.Link href="/">Sign In</Nav.Link>
          <Nav.Link href="/signup">Sign Up</Nav.Link>
          
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavBar;
