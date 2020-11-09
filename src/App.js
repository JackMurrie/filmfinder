import React, { useCallback, useState } from "react";
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
import PublicProfile from './PublicProfile';
import SearchResults from './SearchResults';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import useMediaQuery from '@material-ui/core/useMediaQuery';
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';

function App() {

    const [darkMode, setDarkMode] = useState(useMediaQuery('(prefers-color-scheme: dark)'));

    const theme = React.useMemo(
    () =>
        createMuiTheme({
        palette: {
            type: darkMode ? 'dark' : 'light',
            primary: {
                main: '#42a5f5',
              },
            secondary: {
                main: "#aa647b",
            }
        },
        }),
    [darkMode],
    );

    const handleThemeChange = useCallback( 
        event => {
          event.preventDefault();
          setDarkMode(!darkMode);
          console.log(theme.palette.type);
        },
        [darkMode]
    );


  return (
    <Router>
        <ThemeProvider theme={theme}>
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
            <Route exact path="/Movie/:MovieID">
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
            <Route exact path="/user/:userId">
                <PublicProfile />
            </Route>
            <Route path="/SearchResults">
                <SearchResults />
            </Route>
            <Route path="/">
                <Home darkMode={darkMode} handleThemeChange={handleThemeChange}/>
            </Route>
        </Switch>
        </ThemeProvider>
    </Router>
  );
}

export default App;
