import { bootstrapApplication } from '@angular/platform-browser';
import { AppComponent } from './app/app.component'; // Your root component

bootstrapApplication(AppComponent).catch(err => console.error(err));
