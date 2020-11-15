import React, { useCallback, useEffect, useState } from "react";
import './css/App.css';
import Home from './Home';
import Login from './Login';
import SignUp from './SignUp';
import AccountConf from './AccountConf';
import Movie from './Movie';
import ForgotPass from './ForgotPass';
import ResetPassword from './ResetPassword';
import NewPassConf from './NewPassConf';
import Account from './Account';
import PublicProfile from './PublicProfile';
import SearchResults from './SearchResults';
import Browse from './Browse';
import FilmPoker from './FilmPoker/FilmPoker';
import CreateGame from './FilmPoker/CreateGame';
import JoinGame from './FilmPoker/JoinGame';
import PlayFilmPoker from './FilmPoker/PlayFilmPoker';
import Page404 from './404';
import {BrowserRouter as Router, Switch, Route} from 'react-router-dom';
import useMediaQuery from '@material-ui/core/useMediaQuery';
import { createMuiTheme, ThemeProvider } from '@material-ui/core/styles';
import { useHistory, withRouter } from "react-router-dom";

function usePersistedState(key, defaultValue) {
    const [state, setState] = React.useState(
        () => JSON.parse(localStorage.getItem(key)) || defaultValue
    );
    useEffect(() => {
      localStorage.setItem(key, JSON.stringify(state));
    }, [key, state]);
    return [state, setState];
  }

function App() {
    const [loggedIn, setLoggedIn] = usePersistedState(1, false);

    const handleLogin = useCallback( 
        event => {
          setLoggedIn(true);
        },
        [loggedIn]
    );

    const handleLogout = useCallback( 
        event => {
          setLoggedIn(false);
        },
        [loggedIn]
    );

    const [darkMode, setDarkMode] = usePersistedState(2, useMediaQuery('(prefers-color-scheme: dark)'));

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
        },
        [darkMode]
    );


  return (
    <Router>
        <ThemeProvider theme={theme}>
        <Switch>
            <Route path="/Login">
                <Login handleLogin={handleLogin}/>
            </Route>
            <Route path="/Signup">
                <SignUp handleLogin={handleLogin}/>
            </Route>
            <Route path="/AccountConf">
                <AccountConf loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route exact path="/Movie/:MovieID">
                <Movie loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/ForgotPass">
                <ForgotPass loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/ResetPassword">
                <ResetPassword loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/NewPassConf">
                <NewPassConf loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/Account">
                <Account loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route exact path="/user/:userId">
                <PublicProfile loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route exact path="/SearchResults/:keyword">
                <SearchResults loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/Browse">
                <Browse loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/PokerGame/FilmPoker">
                <FilmPoker loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/PokerGame/Create">
                <CreateGame loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/PokerGame/Join">
                <JoinGame loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route exact path="/PokerGame/Play/:GameID">
                <PlayFilmPoker loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>  
            <Route path="/404">
                <Page404 loggedIn={loggedIn} handleLogout={handleLogout}/>
            </Route>
            <Route path="/">
                <Home loggedIn={loggedIn} darkMode={darkMode} handleLogout={handleLogout} handleThemeChange={handleThemeChange}/>
            </Route>
        </Switch>
        </ThemeProvider>
    </Router>
  );
}

export default App;
