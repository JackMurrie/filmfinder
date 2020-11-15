import React from 'react';
import Button from '@material-ui/core/Button';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import { makeStyles } from '@material-ui/core/styles';
import Card from '@material-ui/core/Card';
import CardHeader from '@material-ui/core/CardHeader';
import Link from '@material-ui/core/Link';
import IconButton from '@material-ui/core/IconButton';
import DeleteIcon from '@material-ui/icons/Delete';
import EditIcon from '@material-ui/icons/Edit';
import CardContent from '@material-ui/core/CardContent';
import Rating from '@material-ui/lab/Rating';
import Box from '@material-ui/core/Box';
import TextField from '@material-ui/core/TextField';
import Dialog from '@material-ui/core/Dialog';
import DialogActions from '@material-ui/core/DialogActions';
import DialogContent from '@material-ui/core/DialogContent';
import DialogContentText from '@material-ui/core/DialogContentText';
import DialogTitle from '@material-ui/core/DialogTitle';

import { useAsync } from 'react-async';
import { CardActions } from '@material-ui/core';

const useStyles = makeStyles((theme) => ({
    '@global': {
      ul: {
        margin: 0,
        padding: 0,
        listStyle: 'none',
      },
    },
    paper: {
      padding: theme.spacing(2),
      display: 'flex',
      overflow: 'auto',
      flexDirection: 'column',
    },
    right: {
      marginLeft: 900,
    },
    title: {
      color: theme.palette.text.primary
    },
    control: {
      marginLeft: 'auto'
    }
  })
);

export default function PrivateReview(props) {
    const classes = useStyles();

    const requestOptions = {
      method: 'DELETE',
      headers: { 
        'Accept': 'application/json',
      }
    };
    
    const removeReviewAndReload = async () => {
      await fetch(`/rest/review/${props.movieId}`, requestOptions);

      props.onChange();
    };

    const deleteReview = useAsync({ deferFn: removeReviewAndReload });

    return (
        <Grid item xs={12}>
            <Card style={{width: 1150, margin: 10}}>
              <CardHeader
                title={<Link href={`/Movie/${props.movieId}`} className={classes.title} style={{ fontSize: '30px' }}>{props.title}</Link>}
                action={
                  <Box component="fieldset" mb={-1} borderColor="transparent" marginTop={5}>
                    <Rating name="read-only" precision={0.5} value={props.rating} readOnly/>
                  </Box>
                }
              />
            <CardContent>
              {props.text}
            </CardContent>
            <CardActions className={classes.right}>
              <EditReviewButton />
              <IconButton color="primary" component="span" className={classes.control} onClick={deleteReview.run}>
                <DeleteIcon />
              </IconButton>
              <Button disabled color="secondary">
                {props.postDate}
            </Button>
            </CardActions>
          </Card>
        </Grid>
    );
  }

  function EditReviewButton(props) {
    const classes = useStyles();
    const [open, setOpen] = React.useState(false);
    const [newComment, setNewComment] = React.useState('');
  
    const openReviewDialogBox = () => {
      setOpen(true);
    };
  
    const closeReviewDialogBox = () => {
      setOpen(false);
    };
  
    const submitReview = (event) => {
      event.preventDefault();

      console.log(newComment)

    };
  
    return (
      <div>
        <IconButton color="primary" component="span" className={classes.control} onClick={openReviewDialogBox}>
              <EditIcon />
        </IconButton>
        <Dialog fullwidth open={open} onClose={closeReviewDialogBox} aria-labelledby="form-dialog-title">
          <form onSubmit={submitReview}>
            <DialogTitle id="form-dialog-title">Review</DialogTitle>
            <DialogContent>
              <DialogContentText>
                Edit your review here...
              </DialogContentText>
              <TextField
                autoFocus
                multiline
                rowsMax={10}
                margin="dense"
                id="review"
                label=""
                fullWidth
                defaultValue="My old review goes here" 
                onChange={(event) => setNewComment(event.target.value)}
              />
            </DialogContent>
            <DialogActions>
              <Button onClick={closeReviewDialogBox} color="primary">
                Cancel
              </Button>
              <Button onClick={closeReviewDialogBox} color="primary" type="submit">
                Edit Review
              </Button>
            </DialogActions>
          </form>
        </Dialog>
      </div>
    );
  }