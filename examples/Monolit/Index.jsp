using System;
//@ USE_IN Kapitola1:Kapitola5; Kapitola7; 
using System.Collections.Generic;
//@ USE_END
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Data.SqlClient;
using System.Threading;
using System.Data;

namespace Ukol6
{

    /**
     * Model for DB access
     **/
    class Model
    {

        private MainWindow window;
        private String customerId { set; get; } //@ USE_IN Kapitola3;

        /**
         * Construct model with gui and customerID
         * @param MainWindow window //@ USE_IN Kapitola2:?; 
         * @param String customerID //@ USE_IN Kapitola3:?;
         **/

        //@ USE_IN Kapitola2;
        public Model(MainWindow window)
        {
            this.window = window;
        }
        //@ USE_END

        //@ USE_IN Kapitola3:?;
        public Model(MainWindow window, String customerID)
        {
            this.window = window;
            this.customerId = customerID;
        }
        //@ USE_END
