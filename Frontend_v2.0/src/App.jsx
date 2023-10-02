import "./App.css";
import { BrowserRouter as Router, Switch, Route } from "react-router-dom";
import { Row, Col } from "react-bootstrap";
import signin from "./Components/signin";
import signup from "./Components/signup";
import Welcome from "./Components/Welcome";
import "bootstrap/dist/css/bootstrap.min.css";
import "font-awesome/css/font-awesome.min.css";
import Conformation from "./Components/Conformation";
import AddFood from "./Components/AddFood";
import Transaction from "./Components/Transactions";
import WelcomeA from "./Components/WelcomeA";
import Admin from "./Components/Admin";
import OrderDetails from "./Components/OrderDetails";
import UpdateFood from './Components/UpdateFood';
import ChangeUser from './Components/ChangeUser'

function App() {
  return (
    <Router>
      <div className="App">
        <Row>
          <Col>
            <Switch>
              <Route path="/" exact component={signin} />
              <Route path="/signup" exact component={signup} />
              <Route path="/welcome" exact component={Welcome} />
              <Route path="/admin" exact component={Admin} />
              <Route path="/user" exact component={ChangeUser} />
              <Route path="/add" exact component={AddFood} />
              <Route path="/welcomea" exact component={WelcomeA} />
              <Route path="/conformation" exact component={Conformation} />
              <Route path="/transactions" exact component={Transaction} />
              <Route path="/orderDetails" exact component={OrderDetails} />
              <Route path="/update" exact component={UpdateFood} />
            </Switch>
          </Col>
        </Row>
      </div>
    </Router>
  );
}

export default App;
