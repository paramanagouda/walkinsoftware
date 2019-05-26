import { RouterModule, Routes } from '@angular/router';
import { LoginComponent } from "./login/login.component";
import { DashboardComponent } from "./dashboard/dashboard.component";

const routes: Routes = [
  {
    path: '',
     component: LoginComponent
    },
  {
    path: 'login',
     component: LoginComponent
    },
  {
    path : 'dashboard',
     component : DashboardComponent
   }
];

export const routing = RouterModule.forRoot(routes);
