import Vue from 'vue'
import Router from 'vue-router'
import Login from '../views/Login.vue'
import Logout from '../views/Logout.vue'
import Register from '../views/Register.vue'
import store from '../store/index'
import Home from '../views/Home.vue'
import Cards from '../views/Cards.vue'
import CreateNewDeck from '../views/CreateNewDeck.vue'
import ScoreSummary from '@/views/ScoreSummary.vue'
import StudySession from '@/views/StudySession.vue'
import EditCard from '@/views/EditCard.vue'
import EditDeck from '@/views/EditDeck.vue'
import CreateNewCard from '@/views/CreateNewCard.vue'
import SearchResults from "@/views/SearchResults";

Vue.use(Router)

/**
 * The Vue Router is used to "direct" the browser to render a specific view component
 * inside of App.vue depending on the URL.
 *
 * It also is used to detect whether or not a route requires the user to have first authenticated.
 * If the user has not yet authenticated (and needs to) they are redirected to /login
 * If they have (or don't need to) they're allowed to go about their way.
 */

const router = new Router({
  mode: 'history',
  base: process.env.BASE_URL,
  routes: [
    {
      path: '/',
      name: 'home',
      component: Home,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/login",
      name: "login",
      component: Login,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/logout",
      name: "logout",
      component: Logout,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/register",
      name: "register",
      component: Register,
      meta: {
        requiresAuth: false
      }
    },
    {
      path: "/deck/:id/cards",
      name: "cards",
      component: Cards,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/deck/new-deck",
      name: "new-deck",
      component: CreateNewDeck,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/deck/:id/new-card",
      name: "new-card",
      component: CreateNewCard,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/deck/:id/score-summary",
      name: "score-summary",
      component: ScoreSummary,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/deck/:deckId/cards/:cardId/edit-card",
      name: "edit-card",
      component: EditCard,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/deck/:id/edit-deck",
      name: "edit-deck",
      component: EditDeck,
      meta: {
        requiresAuth: true
      }
    },
    {
      path: "/deck/:id/study-session",
      name: "study-session",
      component: StudySession
    }, {
      path: "/search/:searchInput",
      name: "search-results",
      component: SearchResults,
      meta: {
        requiresAuth: true,
      }
    },
  ]
})

router.beforeEach((to, from, next) => {
  // Determine if the route requires Authentication
  const requiresAuth = to.matched.some(x => x.meta.requiresAuth);

  // If it does and they are not logged in, send the user to "/login"
  if (requiresAuth && store.state.token === '') {
    next("/login");
  } else {
    // Else let them go to their next destination
    next();
  }
});

export default router;
