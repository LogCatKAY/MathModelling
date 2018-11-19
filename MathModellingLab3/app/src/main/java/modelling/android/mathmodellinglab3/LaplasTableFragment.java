package modelling.android.mathmodellinglab3;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class LaplasTableFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private LaplasAdapter mLaplasAdapter;

    private static int mLaplasTableToShow;

    public static LaplasTableFragment newInstance(int whichTable){
        mLaplasTableToShow = whichTable;
        return new LaplasTableFragment();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.laplas_table_fragment, container, false);
        setHasOptionsMenu(true);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.table_recycler_view);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUi();

        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUi();
    }

    private class LaplasHolder extends RecyclerView.ViewHolder {
        private TextView mTextArgX;
        private TextView mTextFx;
        private LaplasItem mLaplasItem;

        public LaplasHolder(LayoutInflater inflater, ViewGroup parent){
            super(inflater.inflate(R.layout.list_laplas_item, parent, false));

            mTextArgX = (TextView) itemView.findViewById(R.id.tv_item_x);
            mTextFx = (TextView) itemView.findViewById(R.id.tv_item_fx);
        }

        public void bind(LaplasItem laplasItem) {
            mLaplasItem = laplasItem;
            mTextArgX.setText(String.valueOf(mLaplasItem.getX()));
            mTextFx.setText(String.valueOf(mLaplasItem.getFx()));
        }
    }

    private class LaplasAdapter extends RecyclerView.Adapter<LaplasHolder> {
        private List<LaplasItem> mLaplasItems;

        public LaplasAdapter(List<LaplasItem> laplasItems) {
            mLaplasItems = laplasItems;
        }

        @NonNull
        @Override
        public LaplasHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            return new LaplasHolder(layoutInflater, parent);
        }

        @Override
        public void onBindViewHolder(@NonNull LaplasHolder holder, int position) {
            LaplasItem laplasItem = mLaplasItems.get(position);
            holder.bind(laplasItem);
        }

        @Override
        public int getItemCount() {
            return mLaplasItems.size();
        }

        public void setLaplasItems(List<LaplasItem> laplasItems) {
            mLaplasItems = laplasItems;
        }
    }

    public void updateUi() {
        LaplasFunctionTable laplasFunctionTable = LaplasFunctionTable.get(getActivity());
        List<LaplasItem> laplasItems;

        switch (mLaplasTableToShow){
            case R.id.nav_first:
                laplasItems = laplasFunctionTable.getLaplas1Items();
                break;
            case R.id.nav_second:
                laplasItems = laplasFunctionTable.getLaplas2Items();
                break;
            default:
                laplasItems = laplasFunctionTable.getLaplas1Items();
                laplasItems.addAll(laplasFunctionTable.getLaplas2Items());
                break;
        }
        if(mLaplasAdapter == null) {
            mLaplasAdapter = new LaplasAdapter(laplasItems);
            mRecyclerView.setAdapter(mLaplasAdapter);
        } else {
            mLaplasAdapter.setLaplasItems(laplasItems);
            mLaplasAdapter.notifyDataSetChanged();
        }
    }

    @Override
    public void onPrepareOptionsMenu(Menu menu) {
        super.onPrepareOptionsMenu(menu);
        MenuItem item = menu.findItem(R.id.menu_nodes_visibility);
        item.setVisible(false);
    }
}
