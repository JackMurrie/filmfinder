import Header from '../components/Header';
import MovieCard from '../components/MovieCard';
import Footer from '../components/Footer';
import PersonCard from './components/personCard';
import PokerCard from './components/pokerCard';
import VoteCard from './components/voteCard';

import React, { useEffect, useState } from 'react';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import { useLocation } from "react-router-dom";
import Async from 'react-async';
import PropTypes from 'prop-types';
import AppBar from '@material-ui/core/AppBar';
import Tabs from '@material-ui/core/Tabs';
import Tab from '@material-ui/core/Tab';
import FavoriteIcon from '@material-ui/icons/Favorite';
import VisibilityIcon from '@material-ui/icons/Visibility';
import ThumbUp from '@material-ui/icons/ThumbUp';
import Box from '@material-ui/core/Box';
import Container from '@material-ui/core/Container';
import SearchIcon from '@material-ui/icons/Search';
import Stepper from "@material-ui/core/Stepper";
import Step from "@material-ui/core/Step";
import StepLabel from "@material-ui/core/StepLabel";
import { useFetch, IfFulfilled, IfPending, IfRejected } from 'react-async';
import Backdrop from '@material-ui/core/Backdrop';
import CircularProgress from '@material-ui/core/CircularProgress';
import { SentimentSatisfied } from '@material-ui/icons';
import _ from 'lodash';

const useStyles = makeStyles((theme) => ({
  paper: {
    margin: theme.spacing(1),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
    flexWrap: "wrap",
    height: 165,
    width: 165,
  },
  textField: {
    width: 500,
    margin: 50,
  },
  resize: {
    fontSize: 50
  },
  buttonSize: {
    width: 300,
    margin: 50,
  },
  headText: {
    textAlign: "center",
    margin: 50,
    fontFamily: ['Montserrat', "sans-serif"],
  },
  largeIcon: {
    width: 125,
    height: 125,
  },
  buttonStyle: {
    marginTop: theme.spacing(8),
    display: 'flex',
    flexDirection: 'column',
    alignItems: 'center',
  },
  resize: {
    fontSize: 20,
  },
  textField: {
    width: 500,
    margin: 20,
  },
  center: {
    alignItems: 'center',
  },
  root: {
    width: "100%"
  },
  button: {
    marginRight: theme.spacing(1),
    marginTop: theme.spacing(5),
    marginBottom: theme.spacing(5)
  },
  instructions: {
    marginTop: theme.spacing(1),
    marginBottom: theme.spacing(1)
  },
  container: {
    display: "flex",
    flexWrap: "wrap",
  },
  right: {
    textAlign: "right",
  },
  center: {
    textAlign: "center",
    alignItems: 'center',
  },
}));

const command = {
  JOIN_GAME: 1,
  GET_NICK: 2,
  ADD_SELECT: 3,
  USER_UPDATED: 4,
  PUSH_GAME: 5,
  VOTE: 6,
  RESULTS: 7,
  PLAYERS: 8
};

export default function PlayFilmPoker(props) {
  const classes = useStyles();
  const location = useLocation();
  const GameID = parseInt(location.pathname.split('/').pop(), 10);
  const nickname = location.state.nickname;

  const [players, setPlayers] = useState([nickname]);
  const [selectedMovies, setSelectedMovies] = useState([]);

  const connect = () => {
    const ws = new WebSocket("ws://localhost:8080/filmpoker");

    ws.onopen = () => {
      const joinGame = {
        command: command.JOIN_GAME,
        nickname: nickname,
        gameId: GameID
      };
  
      ws.send(JSON.stringify(joinGame));
    };

    ws.onmessage = (messageEvent) => { onGameMessage(ws, messageEvent); };

    return () => {
      ws.close({ reason: "Clean up" });
    };
  };
  useEffect(connect, []);

  const onGameMessage = (ws, message) => {
    const data = JSON.parse(message.data);
    switch (data.command) {
      case (command.JOIN_GAME):
        break;

      case (command.GET_NICK):
        break;

      case (command.ADD_SELECT):
        break;

      case (command.USER_UPDATED):
        break;

      case (command.PUSH_GAME):
        break;

      case (command.VOTE):
        break;

      case (command.RESULTS):
        break;

      case (command.PLAYERS):
        setPlayers(data.players);
        break;

      default:
        break;
    }
  }

  const updateSelectedMovies = (addRemoveFlag, selectedMovie) => {
    if (addRemoveFlag) {
      setSelectedMovies((selectedMovies) => ([...selectedMovies, selectedMovie]));
    } else {
      const newSelectedMovies = [...selectedMovies]; 
      _.remove(newSelectedMovies, (movie) => (movie.movieId === selectedMovie.movieId));
      setSelectedMovies(newSelectedMovies);
    }
  }

  const componentScreens = {
    screenLabels: [
      "Choose Movies", 
      "Confirm Movies", 
      "Wait for other Players", 
      "Vote", 
      "View Results"
    ],
    screens: [
    <MovieSelectScreen onChangeMovieSelection={updateSelectedMovies} selectedMovies={selectedMovies}/>,
    <ConfirmSelectionScreen selectedMovies={selectedMovies}/>,
    <WaitPlayersScreen players={players}/>,
    <VotingScreen />,
    <ResultsScreen />
    ]
  };

  return (
    <React.Fragment>
      <CssBaseline />
      <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout}/>
      <Container component="main" maxWidth="lg" >
        <Typography component="h1" variant="h4" className={classes.headText}>
          Hello {nickname}! Invite others to join with your code: {GameID}
        </Typography>
        <ScreenNavigator componentScreens={componentScreens} />
      </Container>
      <Footer />
    </React.Fragment>
  )
}

function TabPanel(props) {
  const { children, value, index, ...other } = props;

  return (
    <div
      role="tabpanel"
      hidden={value !== index}
      id={`scrollable-force-tabpanel-${index}`}
      aria-labelledby={`scrollable-force-tab-${index}`}
      {...other}
    >
      {value === index && (
        <Box p={3}>
          <Typography>{children}</Typography>
        </Box>
      )}
    </div>
  );
}

TabPanel.propTypes = {
  children: PropTypes.node,
  index: PropTypes.any.isRequired,
  value: PropTypes.any.isRequired,
};

function a11yProps(index) {
  return {
    id: `full-width-tab-${index}`,
    'aria-controls': `full-width-tabpanel-${index}`,
  };
}

function MovieSelectScreen(props) {
  const classes = useStyles();
  const [selectedTab, setSelectedTab] = React.useState(0);
  const [searchBox, setSearchBox] = React.useState('');

  const changeSelectedTab = (event, newValue) => {
    setSelectedTab(newValue);
  };

  const requestOptions = {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify({ searchString: searchBox })
  };

  const fetchSearchResults = useFetch('/rest/search', requestOptions, { defer: true });

  useEffect(fetchSearchResults.run, [searchBox]);

  const renderSearchResults = ({ movies }) => {
    const searchResults = movies.map(({ movieId, name, imageUrl }) => {
      const selected = _.some(props.selectedMovies, (selectedMovie) => (selectedMovie.movieId === movieId));
      return (
        <PokerCard
        key={movieId}
        movieId={movieId}
        title={name}
        imageUrl={imageUrl}
        onChangeMovieSelection={props.onChangeMovieSelection}
        selected={selected}
        />
      );
    });

    return searchResults;
  };

  return (
    <div className={classes.root}>
      <AppBar position="static" color="default">
        <Tabs
          value={selectedTab}
          onChange={changeSelectedTab}
          variant="fullWidth"
          indicatorColor="primary"
          textColor="primary"
          aria-label="Buton Tabs"
        >
          <Tab label="From Wishlist" icon={<FavoriteIcon />} {...a11yProps(1)} />
          <Tab label="From Recommended" icon={<ThumbUp />} {...a11yProps(2)} />
          <Tab label="Search" icon={<SearchIcon />} {...a11yProps(3)} />

        </Tabs>
      </AppBar>
      <TabPanel value={selectedTab} index={0}>
        <PokerCard title={"A Movie"} />
      </TabPanel>
      <TabPanel value={selectedTab} index={1}>
        <PokerCard title={"A Movie"} />
      </TabPanel>
      <TabPanel value={selectedTab} index={2}>
        <div className={classes.center}>
          <TextField
            variant="outlined"
            margin="normal"
            fullWidth
            id="search"
            placeholder="Search Movies"
            name="search"
            size='large'
            onChange={(event) => setSearchBox(event.target.value)}
            InputProps={{
              classes: {
                input: classes.resize,
              },
            }}
            className={classes.textField}
          />
        </div>
        <div className={classes.container}>
          <IfRejected state={fetchSearchResults}>Error...</IfRejected>
          <IfPending state={fetchSearchResults}>
          </IfPending>
          <IfFulfilled state={fetchSearchResults}>{renderSearchResults}</IfFulfilled>
        </div>
      </TabPanel>
    </div>
  );
}

function ConfirmSelectionScreen(props) {
  return (
    <React.Fragment>
      <Grid container spacing={3} >
        {
          props.selectedMovies.map(({ movieId, title, imageUrl }) =>
            <PokerCard key={movieId} movieId={movieId} title={title} imageUrl={imageUrl} disableClick/>
          )
        }
      </Grid>
    </React.Fragment>
  );
};

function WaitPlayersScreen(props) {
  return (
    <Grid container spacing={3} >
      {props.players.map((name) => (
        <PersonCard name={name} />
      ))}
    </Grid>
  );
};

function VotingScreen(props) {
  const classes = useStyles();

  return (
    <React.Fragment>
      <Typography component="h1" variant="h4" className={classes.headText}>
        Vote Now!
      </Typography>
      <div className={classes.container}>
        <VoteCard onVoteMovie={props.onVoteMovie} />
      </div>
    </React.Fragment>
  );
};

function ResultsScreen(props) {
  return <div></div>;
};

function ScreenNavigator(props) {
  const classes = useStyles();
  const [activeStep, setActiveStep] = React.useState(0);

  const { screenLabels, screens } = props.componentScreens;

  const handleNext = () => {
    setActiveStep((prevActiveStep) => prevActiveStep + 1);
  };

  const handleBack = () => {
    setActiveStep((prevActiveStep) => prevActiveStep - 1);
  };

  return (
    <div className={classes.root}>
      <div>
        <Button disabled={activeStep === 0 || activeStep > 1} onClick={handleBack} className={classes.button}>
          Back
        </Button>
        {
          (activeStep === screenLabels.length - 1) ?
            <Button variant="contained" color="primary" className={classes.button} href="/">
              Finish
            </Button>
          :
            <Button variant="contained" color="primary" onClick={handleNext} className={classes.button}>
              Next
            </Button>
        }
      </div>
      <Stepper activeStep={activeStep}>
        {screenLabels.map((label) => {
          return (
            <Step key={label}>
              <StepLabel>{label}</StepLabel>
            </Step>
          );
        })}
      </Stepper>
      <div>
        <Typography component={'span'} className={classes.instructions}>
          {screens[activeStep]}
        </Typography>
        <div>
          <Button disabled={activeStep === 0 || activeStep > 1} onClick={handleBack} className={classes.button}>
            Back
          </Button>
          {
            (activeStep === screenLabels.length - 1) ?
              <Button variant="contained" color="primary" className={classes.button} href="/">
                Finish
              </Button>
            :
              <Button variant="contained" color="primary" onClick={handleNext} className={classes.button}>
                Next
              </Button>
          }
        </div>
      </div>
    </div>
  );
}
