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
import { useLocation, useHistory } from "react-router-dom";
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
  resultAlign: {
    marginLeft: 470,
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
  const history = useHistory();
  var nickname = "";

  if (location.state === undefined) {
      history.push("/404");
  }
  else {
    nickname = location.state.nickname;
  }

  const [players, setPlayers] = useState([]);
  const [selectedMovies, setSelectedMovies] = useState([]);
  const [connection, setConnection] = useState(null);
  const [movieGamePool, setMovieGamePool] = useState([]);
  const [votedMovies, setVotedMovies] = useState([]);
  const [movieResults, setMovieResults] = useState([]);

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

    ws.onmessage = onGameMessage;

    setConnection(ws);

    return () => {
      ws.close();
    };
  };
  useEffect(connect, []);

  const onGameMessage = (messageEvent) => {
    let data;
    try {
      data = JSON.parse(messageEvent.data);
    } catch (err) {
      console.log(err);
      console.log(messageEvent.data);
      // Redirect to something went wrong page here...
      return;
    };

    switch (data.command) {
      case (command.JOIN_GAME):
        break;

      case (command.GET_NICK):
        break;

      case (command.ADD_SELECT):
        break;

      case (command.USER_UPDATED):
        setPlayers((players) => {
          const updatedPlayers = players.map((player) => {
            if (player.nickname === data.nickname) {
              return { ...player, ready: true };
            } else {
              return player;
            };
          });
          return updatedPlayers;
        });
        break;

      case (command.PUSH_GAME):
        setPlayers((players) => {
          return players.map((player) => ({ ...player, ready: true }));
        });
        setMovieGamePool(data.selectedMovies);
        break;

      case (command.VOTE):
        break;

      case (command.RESULTS):
        setMovieResults({
          orderedMovies: data.orderedMovies,
          points: data.points
        });
        break;

      case (command.PLAYERS):
        setPlayers((players) => {
          const compareName = (x, y) => (x === y.nickname);
          const newPlayerNames = _.differenceWith(data.players, players, compareName);
          const newPlayers = newPlayerNames.map((newPlayerName) => ({
            nickname: newPlayerName,
            ready: false
          }));
          return _.intersectionWith([...players, ...newPlayers], data.players, _.flip(compareName));
        });
        break;

      default:
        break;
    }
  }

  const updateSelectedMovies = (addRemoveFlag, selectedMovie) => {
    setSelectedMovies((selectedMovies) => {
      if (addRemoveFlag) {
        return [...selectedMovies, selectedMovie];
      } else {
        const newSelectedMovies = [...selectedMovies];
        return _.remove(newSelectedMovies, (movie) => (movie.movieId === selectedMovie.movieId));
      };
    });
  };

  const sendSelectedMovies = () => {
    const addSelect = {
      command: command.ADD_SELECT,
      selectedMovies: selectedMovies.map(({ movieId }) => (movieId))
    };

    connection.send(JSON.stringify(addSelect));
  };

  const updateVotedMovies = (addRemoveFlag, votedMovie) => {
    setVotedMovies((votedMovies) => {
      if (addRemoveFlag) {
        return [...votedMovies, votedMovie];
      } else {
        const newVotedMovies = [...votedMovies];
        return _.remove(newVotedMovies, (movie) => (movie.movieId === votedMovie.movieId));
      };
    });
  };

  const sendVotedMovies = () => {
    const addVote = {
      command: command.VOTE,
      votes: votedMovies.map(({ movieId }) => ({
        movieId: movieId,
        preference: 1
      }))
    };

    connection.send(JSON.stringify(addVote));
  };

  const componentScreens = {
    screenLabels: [
      "Choose Movies",
      "Confirm Movies",
      "Wait for other Players",
      "Vote",
      "View Results"
    ],
    screens: [
      <MovieSelectScreen onChangeMovieSelection={updateSelectedMovies} selectedMovies={selectedMovies} />,
      <ConfirmSelectionScreen selectedMovies={selectedMovies} onNextScreen={sendSelectedMovies} />,
      <WaitPlayersScreen players={players} />,
      <VotingScreen 
        moviesToVote={movieGamePool} 
        votedMovies={votedMovies} 
        onChangeMovieVote={updateVotedMovies}
        onNextScreen={sendVotedMovies}
      />,
      <ResultsScreen movieResults={movieResults} />
    ]
  };

  return (
    <React.Fragment>
      <CssBaseline />
      <Header isLoggedIn={props.loggedIn} handleLogout={props.handleLogout} />
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
    body: JSON.stringify({ title: searchBox })
  };

  const fetchSearchResults = useFetch('/rest/search', requestOptions, { defer: true });
  const fetchDashboard = useFetch('/rest/user/dashboard', requestOptions, { defer: true });

  useEffect(fetchSearchResults.run, [searchBox]);
  useEffect(fetchDashboard.run, []);

  const renderResults = (results) => {
    const componentResults = results.map(({ movieId, name, imageUrl }) => {
      const selected = _.some(props.selectedMovies, (selectedMovie) => (selectedMovie.movieId === movieId));
      return (
        <PokerCard
          key={movieId}
          movieId={movieId}
          title={name}
          imageUrl={imageUrl}
          onChangeSelection={props.onChangeMovieSelection}
          selected={selected}
        />
      );
    });

    return <Grid container spacing={3}>{componentResults}</Grid>;
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
        <IfPending state={fetchDashboard}>
          {/* {() => <CircularProgress color="inherit"/>} */}
        </IfPending>
        <IfFulfilled state={fetchDashboard}>
          {({ wishlist }) => {
            return renderResults(wishlist.movies);
          }}
        </IfFulfilled>
      </TabPanel>
      <TabPanel value={selectedTab} index={1}>
        <IfPending state={fetchDashboard}>
          {/* {() => <CircularProgress color="inherit"/>} */}
        </IfPending>
        <IfFulfilled state={fetchDashboard}>
          {({ recommendations }) => {
            return renderResults(recommendations.movies);
          }}
        </IfFulfilled>
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
          <IfFulfilled state={fetchSearchResults}>
            {({ movies }) => (renderResults(movies))}
          </IfFulfilled>
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
            <PokerCard key={movieId} movieId={movieId} title={title} imageUrl={imageUrl} disableClick />
          )
        }
      </Grid>
    </React.Fragment>
  );
};

function WaitPlayersScreen(props) {
  return (
    <Grid container spacing={3} >
      {props.players.map(({ nickname, ready }) => (
        <PersonCard key={nickname} name={nickname} ready={ready} />
      ))}
    </Grid>
  );
};

function VotingScreen(props) {
  const classes = useStyles();
  const renderResults = (results) => {
    const componentResults = results.map(({ movieId, name, imageUrl }) => {
      const selected = _.some(props.votedMovies, (votedMovie) => (votedMovie.movieId === movieId));
      return (
        <PokerCard
          key={movieId}
          movieId={movieId}
          title={name}
          imageUrl={imageUrl}
          onChangeSelection={props.onChangeMovieVote}
          selected={selected}
        />
      );
    });

    return <Grid container spacing={3}>{componentResults}</Grid>;
  };

  return (
    <React.Fragment>
      <Typography component="h1" variant="h4" className={classes.headText}>
        Vote Now!
      </Typography>
      <div className={classes.container}>
        {renderResults(props.moviesToVote)}
      </div>
    </React.Fragment>
  );
};

function ResultsScreen(props) {
  const classes = useStyles();
  // props.movieResults has format:
  // { 
  //   orderedMovies: [movieInfo...],
  //   points: [int]
  // }
  const { points, orderedMovies } = props.movieResults;
  console.log(points);
  console.log(orderedMovies);
  return (
    <div>
      { _.zipWith(points, orderedMovies, ((score, movie) => (
          <React.Fragment>
            <Typography component="h1" variant="h4" className={classes.headText}>
              {score}
            </Typography>
            <div className={classes.resultAlign}>
              <PokerCard
                key={movie.movieId}
                movieId={movie.movieId}
                title={movie.name}
                imageUrl={movie.imageUrl}
                disableClick
              />
            </div>
          </React.Fragment>
        )))
      }
    </div>
  );
};

function ScreenNavigator(props) {
  const classes = useStyles();
  const [activeStep, setActiveStep] = React.useState(0);

  const { screenLabels, screens } = props.componentScreens;

  const handleNext = () => {
    if (screens[activeStep].props.onNextScreen) {
      screens[activeStep].props.onNextScreen();
    };
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
        {screens[activeStep]}
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
