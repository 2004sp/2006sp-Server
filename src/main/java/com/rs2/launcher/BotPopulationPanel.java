package com.rs2.launcher;

import com.rs2.Server;
import com.rs2.ServerSettings;
import com.rs2.bot.combat.WildernessBotSettings;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public final class BotPopulationPanel extends JPanel implements ActionListener {
    private static final String LOCKED_MESSAGE = "Please shutdown server to edit bot population";
    private final JTextField pvpBotCountField = new JTextField(8);
    private final JTextField skillingBotCountField = new JTextField(8);
    private final JTextField progressiveBotCountField = new JTextField(8);
    private final JTextField tradeBotCountField = new JTextField(8);
    private final JTextField clanWarsBotCountField = new JTextField(8);
    private final JTextField otherBotCountField = new JTextField(8);
    private final JTextField[] populationFields = new JTextField[]{
        this.pvpBotCountField,
        this.skillingBotCountField,
        this.progressiveBotCountField,
        this.tradeBotCountField,
        this.clanWarsBotCountField,
        this.otherBotCountField
    };
    private final JButton saveButton = new JButton("Save Bot Population");
    private final JLabel totalLabel = new JLabel();

    public BotPopulationPanel(Font font) {
        super(new GridBagLayout());
        this.setBorder(javax.swing.BorderFactory.createEmptyBorder(12, 16, 12, 16));

        MouseAdapter lockedFieldListener = new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent mouseEvent) {
                if (BotPopulationPanel.this.isLocked()) {
                    BotPopulationPanel.this.showLockedMessage();
                }
            }
        };

        for (int index = 0; index < this.populationFields.length; ++index) {
            this.populationFields[index].setFont(font);
            this.populationFields[index].addMouseListener(lockedFieldListener);
        }

        this.saveButton.setFont(font);
        this.saveButton.addActionListener(this);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(4, 4, 4, 8);
        constraints.weightx = 1.0;

        int row = 0;
        row = this.addPopulationRow("PvP / Wilderness:", this.pvpBotCountField, row, constraints, font);
        row = this.addPopulationRow("Skilling:", this.skillingBotCountField, row, constraints, font);
        row = this.addPopulationRow("Progressive:", this.progressiveBotCountField, row, constraints, font);
        row = this.addPopulationRow("Trade:", this.tradeBotCountField, row, constraints, font);
        row = this.addPopulationRow("Clan Wars (per team):", this.clanWarsBotCountField, row, constraints, font);
        row = this.addPopulationRow("Other / social:", this.otherBotCountField, row, constraints, font);

        JLabel zeroNote = new JLabel("Set a population to 0 to disable that bot style.");
        zeroNote.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = row++;
        constraints.gridwidth = 2;
        constraints.weightx = 1.0;
        this.add(zeroNote, constraints);

        JLabel clanNote = new JLabel("Clan Wars uses two equal teams, so its total population is twice the value shown.");
        clanNote.setFont(font);
        constraints.gridy = row++;
        this.add(clanNote, constraints);

        this.totalLabel.setFont(font);
        constraints.gridy = row++;
        this.add(this.totalLabel, constraints);

        constraints.gridy = row;
        constraints.gridwidth = 1;
        constraints.gridx = 0;
        constraints.weightx = 0.0;
        this.add(this.saveButton, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        this.add(new JLabel(""), constraints);

        this.loadCurrentValues();
        this.refreshLockState();
    }

    private int addPopulationRow(String labelText, JTextField field, int row, GridBagConstraints constraints, Font font) {
        JLabel label = new JLabel(labelText);
        label.setFont(font);
        constraints.gridx = 0;
        constraints.gridy = row;
        constraints.gridwidth = 1;
        constraints.weightx = 0.0;
        this.add(label, constraints);

        constraints.gridx = 1;
        constraints.weightx = 1.0;
        this.add(field, constraints);
        return row + 1;
    }

    private void loadCurrentValues() {
        this.pvpBotCountField.setText(Integer.toString(ServerSettings.wildyBotsEnabled ? WildernessBotSettings.wildyBotCount : 0));
        this.skillingBotCountField.setText(Integer.toString(ServerSettings.skillingBotsEnabled ? ServerSettings.skillingBotCount : 0));
        this.progressiveBotCountField.setText(Integer.toString(ServerSettings.progressiveBotsEnabled ? ServerSettings.progressiveBotCount : 0));
        this.tradeBotCountField.setText(Integer.toString(ServerSettings.tradeBotsEnabled ? ServerSettings.tradeBotCount : 0));
        this.clanWarsBotCountField.setText(Integer.toString(ServerSettings.clanWarsBotsEnabled ? ServerSettings.clanWarsTeamSize : 0));
        this.otherBotCountField.setText(Integer.toString(ServerSettings.otherBotsEnabled ? ServerSettings.otherBotCount : 0));
        this.updateTotalLabel();
    }

    public void refreshLockState() {
        boolean editable = !this.isLocked();
        for (int index = 0; index < this.populationFields.length; ++index) {
            this.populationFields[index].setEditable(editable);
        }
    }

    public boolean applyPendingChanges(boolean showSuccessMessage) {
        if (this.isLocked()) {
            this.showLockedMessage();
            return false;
        }

        try {
            int pvpCount = this.parsePopulation(this.pvpBotCountField, "PvP / Wilderness");
            int skillingCount = this.parsePopulation(this.skillingBotCountField, "Skilling");
            int progressiveCount = this.parsePopulation(this.progressiveBotCountField, "Progressive");
            int tradeCount = this.parsePopulation(this.tradeBotCountField, "Trade");
            int clanWarsTeamSize = this.parsePopulation(this.clanWarsBotCountField, "Clan Wars");
            int otherCount = this.parsePopulation(this.otherBotCountField, "Other / social");

            long totalCount = (long)pvpCount + (long)skillingCount + (long)progressiveCount
                + (long)tradeCount + (long)clanWarsTeamSize * 2L + (long)otherCount;
            if (totalCount >= (long)ServerSettings.botLoginIdLimit) {
                JOptionPane.showMessageDialog(
                    this,
                    "Total bot population must be less than " + ServerSettings.botLoginIdLimit + ".",
                    "Bots",
                    JOptionPane.WARNING_MESSAGE
                );
                return false;
            }

            WildernessBotSettings.wildyBotCount = pvpCount;
            ServerSettings.wildyBotsEnabled = pvpCount > 0;

            ServerSettings.skillingBotCount = skillingCount;
            ServerSettings.skillingBotsEnabled = skillingCount > 0;

            ServerSettings.progressiveBotCount = progressiveCount;
            ServerSettings.progressiveBotsEnabled = progressiveCount > 0;

            ServerSettings.tradeBotCount = tradeCount;
            ServerSettings.tradeBotsEnabled = tradeCount > 0;

            ServerSettings.clanWarsTeamSize = clanWarsTeamSize;
            ServerSettings.clanWarsBotsEnabled = clanWarsTeamSize > 0;

            ServerSettings.otherBotCount = otherCount;
            ServerSettings.otherBotsEnabled = otherCount > 0;

            this.persistBotPopulation();
            this.updateTotalLabel();

            if (showSuccessMessage) {
                JOptionPane.showMessageDialog(
                    this,
                    "Bot population saved. The values will be used the next time the server starts.",
                    "Bots",
                    JOptionPane.INFORMATION_MESSAGE
                );
            }
            return true;
        }
        catch (NumberFormatException numberFormatException) {
            JOptionPane.showMessageDialog(
                this,
                numberFormatException.getMessage(),
                "Bots",
                JOptionPane.WARNING_MESSAGE
            );
            return false;
        }
        catch (IOException ioException) {
            JOptionPane.showMessageDialog(
                this,
                "Unable to save bot population to config/server.cfg: " + ioException.getMessage(),
                "Bots",
                JOptionPane.ERROR_MESSAGE
            );
            ioException.printStackTrace();
            return false;
        }
    }

    private int parsePopulation(JTextField field, String label) {
        String text = field.getText().trim();
        int value;
        try {
            value = Integer.parseInt(text);
        }
        catch (NumberFormatException numberFormatException) {
            throw new NumberFormatException(label + " population must be a whole number.");
        }
        if (value < 0) {
            throw new NumberFormatException(label + " population cannot be negative.");
        }
        return value;
    }

    private boolean isLocked() {
        return Server.serverStatus != 0;
    }

    private void showLockedMessage() {
        JOptionPane.showMessageDialog(
            this,
            LOCKED_MESSAGE,
            "Bots",
            JOptionPane.INFORMATION_MESSAGE
        );
    }

    private void updateTotalLabel() {
        try {
            int total = Integer.parseInt(this.pvpBotCountField.getText().trim())
                + Integer.parseInt(this.skillingBotCountField.getText().trim())
                + Integer.parseInt(this.progressiveBotCountField.getText().trim())
                + Integer.parseInt(this.tradeBotCountField.getText().trim())
                + Integer.parseInt(this.clanWarsBotCountField.getText().trim()) * 2
                + Integer.parseInt(this.otherBotCountField.getText().trim());
            this.totalLabel.setText("Total configured bots: " + total);
        }
        catch (NumberFormatException numberFormatException) {
            this.totalLabel.setText("Total configured bots: -");
        }
    }

    private void persistBotPopulation() throws IOException {
        Map<String, String> replacements = new LinkedHashMap<String, String>();
        replacements.put(
            "WILDY_BOTS",
            "[WILDY_BOTS];" + (ServerSettings.wildyBotsEnabled ? 1 : 0)
                + ";" + WildernessBotSettings.wildyBotCount
                + ";" + (ServerSettings.wildyBotsUseNewGeneration ? 1 : 0)
                + ";" + ServerSettings.wildyBotsBaseCombatLevel
                + ";" + ServerSettings.wildyBotsCombatLevelSpread
                + ";" + (ServerSettings.wildyBotsIgnoreCombatForDeepWilderness ? 1 : 0)
        );
        replacements.put(
            "SKILLING_BOTS",
            "[SKILLING_BOTS];" + (ServerSettings.skillingBotsEnabled ? 1 : 0)
                + ";" + ServerSettings.skillingBotCount
        );
        replacements.put(
            "PROGRESSIVE_BOTS",
            "[PROGRESSIVE_BOTS];" + (ServerSettings.progressiveBotsEnabled ? 1 : 0)
                + ";" + ServerSettings.progressiveBotCount
                + ";" + (ServerSettings.progressiveBotsPrioritizeExisting ? 1 : 0)
        );
        replacements.put(
            "TRADE_BOTS",
            "[TRADE_BOTS];" + (ServerSettings.tradeBotsEnabled ? 1 : 0)
                + ";" + ServerSettings.tradeBotCount
        );
        replacements.put(
            "OTHER_BOTS",
            "[OTHER_BOTS];" + (ServerSettings.otherBotsEnabled ? 1 : 0)
        );
        replacements.put(
            "OTHER_BOT_COUNT",
            "[OTHER_BOT_COUNT];" + ServerSettings.otherBotCount
        );
        replacements.put(
            "CLANWAR_BOTS",
            "[CLANWAR_BOTS];" + (ServerSettings.clanWarsBotsEnabled ? 1 : 0)
                + ";" + ServerSettings.clanWarsTeamSize
                + ";" + ServerSettings.clanWarsEventChanceDivisor
        );

        File configFile = new File("config/server.cfg");
        List<String> lines = new ArrayList<String>();
        BufferedReader reader = new BufferedReader(new FileReader(configFile));
        try {
            String line;
            while ((line = reader.readLine()) != null) {
                lines.add(line);
            }
        }
        finally {
            reader.close();
        }

        for (Map.Entry<String, String> entry : replacements.entrySet()) {
            boolean replaced = false;
            String prefix = "[" + entry.getKey() + "]";
            for (int index = 0; index < lines.size(); ++index) {
                if (lines.get(index).trim().startsWith(prefix)) {
                    lines.set(index, entry.getValue());
                    replaced = true;
                    break;
                }
            }
            if (!replaced) {
                lines.add("");
                lines.add(entry.getValue());
            }
        }

        File temporaryFile = new File(configFile.getParentFile(), configFile.getName() + ".tmp");
        BufferedWriter writer = new BufferedWriter(new FileWriter(temporaryFile));
        try {
            for (int index = 0; index < lines.size(); ++index) {
                writer.write(lines.get(index));
                writer.newLine();
            }
        }
        finally {
            writer.close();
        }

        Files.move(
            temporaryFile.toPath(),
            configFile.toPath(),
            StandardCopyOption.REPLACE_EXISTING
        );
    }

    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        if (actionEvent.getSource() == this.saveButton) {
            this.applyPendingChanges(true);
        }
    }
}
