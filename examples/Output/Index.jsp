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

        /**
         * Construct model with gui and customerID
         * @param MainWindow window 
         **/

        public Model(MainWindow window)
        {
            this.window = window;
        }

