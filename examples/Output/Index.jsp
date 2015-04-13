using System;
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
        private String customerId { set; get; } 

        /**
         * Construct model with gui and customerID
         * @param MainWindow window 
         * @param String customerID 
         **/


        public Model(MainWindow window, String customerID)
        {
            this.window = window;
            this.customerId = customerID;
        }
