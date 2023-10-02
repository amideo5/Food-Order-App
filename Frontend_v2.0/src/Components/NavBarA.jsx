import React from "react";
import { Navbar, Nav } from "react-bootstrap";
import Container from "react-bootstrap/Container";

const NavBarA = (props) => {
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
            <Nav.Link href="/welcomea">Admin Home</Nav.Link>
            <Nav.Link href="/add">Add Food</Nav.Link>
            <Nav.Link href="/update">Update Food</Nav.Link>
          </Nav>
          <Nav>
            <Nav.Link onClick={handleLogout} href="/admin">
              Logout
            </Nav.Link>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};

export default NavBarA;
