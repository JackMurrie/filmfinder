import React from 'react';
import './css/App.css';
import Home from './Home';
import Login from './Login';
import SignUp from './SignUp';
import AccountConf from './AccountConf';
import Movie from './Movie';
import ForgotPass from './ForgotPass';
import Verification from './verification';
import ResetPassword from './ResetPassword';
import NewPassConf from './NewPassConf';
import Account from './Account';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';

function App() {
  return (
    <Router>
        <Switch>
            <Route path="/Login">
                <Login />
            </Route>
            <Route path="/Signup">
                <SignUp />
            </Route>
            <Route path="/AccountConf">
                <AccountConf />
            </Route>
            <Route path="/Movie">
                <Movie />
            </Route>
            <Route path="/ForgotPass">
                <ForgotPass />
            </Route>
            <Route path="/verification">
                <Verification />
            </Route>
            <Route path="/ResetPassword">
                <ResetPassword />
            </Route>
            <Route path="/NewPassConf">
                <NewPassConf />
            </Route>
            <Route path="/Account">
                <Account />
            </Route>
            <Route path="/">
                <Home />
            </Route>
        </Switch>
    </Router>
  );
}

export default App;
