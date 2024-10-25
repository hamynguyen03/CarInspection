import * as React from "react";
import * as ReactDOM from "react-dom/client";
import {
  createBrowserRouter,
  RouterProvider,
} from "react-router-dom";
import "./index.css";
import { CarInspectionResultsPage } from './components/CarInspectionResultsPage';
import { ReportPage } from "./components/ReportPage";
import "./App.css"

const router = createBrowserRouter([
  {
    path: "/",
    element: <CarInspectionResultsPage />
  },
  {
    path: "/carinspectionreport",   
    element: <ReportPage />
  }
]);

const root = ReactDOM.createRoot(document.getElementById("root"));
root.render(
  <React.StrictMode>
    <RouterProvider router={router} />
  </React.StrictMode>
);

